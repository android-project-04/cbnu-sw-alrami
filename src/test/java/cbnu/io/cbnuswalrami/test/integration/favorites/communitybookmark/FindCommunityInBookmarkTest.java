package cbnu.io.cbnuswalrami.test.integration.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.FindCommunityInBookmarkService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.communitybookmark.CommunityBookmarkFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("북마크 id값으로 게시물 조회 통합테스트")
public class FindCommunityInBookmarkTest extends DatabaseTestBase {

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private CommunityBookmarkFixture communityBookmarkFixture;

    @Autowired
    private FindCommunityInBookmarkService findCommunityInBookmarkService;


    @DisplayName("북마크 id로 조회하여 적절한 커뮤니티 게시물이 반환된다.")
    @Test
    void given_valid_id_when_find_then_return_community() {
        // given
        ResponseCommunity responseCommunity = communityFixture.writeOneCommunity();
        String expectedTitle = responseCommunity.getTitle();

        ResponseCommunity community = communityBookmarkFixture.saveCommunityToBookmark(responseCommunity);

        // when
        ResponseCommunity actual = findCommunityInBookmarkService.findCommunityByBookmarkId(RequestId.from(community.getId()));

        // then
        assertThat(actual.getTitle()).isEqualTo(expectedTitle);
    }

    @DisplayName("존재하지 않는 북마크 id로 조회할경우 illegalArgumentException을 던진다.")
    @Test
    void given_invalid_id_when_find_then_return_illegalargument_exception() {
        // given
        Long invalidId = 123L;

        // when then
        assertThatThrownBy(() -> findCommunityInBookmarkService.findCommunityByBookmarkId(RequestId.from(invalidId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id의 북마크의 게시물을 찾을 수 없습니다.");
    }
}
