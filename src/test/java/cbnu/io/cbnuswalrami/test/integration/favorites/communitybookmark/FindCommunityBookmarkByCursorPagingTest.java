package cbnu.io.cbnuswalrami.test.integration.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.paging.CommunityBookmarkCursorPagingService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.communitybookmark.CommunityBookmarkFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("즐겨찾기한 게시글 커서 페이징 조회(최신순) 통합테스트")
class FindCommunityBookmarkByCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private CommunityBookmarkCursorPagingService communityBookmarkCursorPagingService;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private CommunityBookmarkFixture communityBookmarkFixture;

    @Autowired
    private MemberFindUtil memberFindUtil;

    @DisplayName("15개의 즐겨찾기 게시글이 있을 때 next값이 5이고 size가 4이면 hasNext가 false이다.")
    @Test
    void given_next_is_5_and_size_4_when_find_by_cursor_then_has_next_should_be_false() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        communityBookmarkFixture.saveCommunitiesToBookmark(responseCommunities);
        Member member = memberFindUtil.findMemberByAuthentication();

        // when
        CursorResult cursorResult = communityBookmarkCursorPagingService.findCommunityBookmarkByCursor(
                Cursor.from(5L, 4L),
                member
        );
        // then
        assertThat(cursorResult.getHasNext()).isFalse();
    }

    @DisplayName("15개의 즐겨찾기 게시글이 있을 때 next값이 5이고 size가 3이면 hasNext가 true이다.")
    @Test
    void given_next_is_5_and_size_3_when_find_by_cursor_then_has_next_should_be_true() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        communityBookmarkFixture.saveCommunitiesToBookmark(responseCommunities);
        Member member = memberFindUtil.findMemberByAuthentication();

        // when
        CursorResult cursorResult = communityBookmarkCursorPagingService.findCommunityBookmarkByCursor(
                Cursor.from(5L, 3L),
                member
        );

        assertThat(cursorResult.getHasNext()).isTrue();
    }

    @DisplayName("size가 11이면 11개의 게시글을 가져온다.")
    @Test
    void given_11_size_when_find_then_return_size_should_be_11() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        communityBookmarkFixture.saveCommunitiesToBookmark(responseCommunities);
        Member member = memberFindUtil.findMemberByAuthentication();

        // when
        CursorResult cursorResult = communityBookmarkCursorPagingService.findCommunityBookmarkByCursor(
                Cursor.from(null, 11L),
                member
        );

        assertThat(cursorResult.getValues().size()).isEqualTo(11);
    }

    @DisplayName("next값을 안주면 가장 최신의 데이터를 가지고 조회한다.")
    @Test
    void given_next_null_when_find_by_cursor_then_resent_id() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        communityBookmarkFixture.saveCommunitiesToBookmark(responseCommunities);
        Member member = memberFindUtil.findMemberByAuthentication();

        // then
        CursorResult cursorResult = communityBookmarkCursorPagingService.findCommunityBookmarkByCursor(
                Cursor.from(null, 1L),
                member
        );

        // then
        assertThat(cursorResult.getLastIndex()).isEqualTo(responseCommunities.size());
    }
}
