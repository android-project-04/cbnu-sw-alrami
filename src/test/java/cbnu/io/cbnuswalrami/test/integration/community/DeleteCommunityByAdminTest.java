package cbnu.io.cbnuswalrami.test.integration.community;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.command.CommunityJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.application.service.DeleteCommunityByAdminService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("어드민 게시물 삭제 통합테스트")
class DeleteCommunityByAdminTest extends DatabaseTestBase {

    @Autowired
    private DeleteCommunityByAdminService deleteCommunityByAdminService;

    @Autowired
    private CommunityJpaRepository communityJpaRepository;

    @Autowired
    private CommunityFixture communityFixture;

    @DisplayName("존재하지 않는 게시물을 삭제하려면 IllegalArgumentException을 던진다.")
    @Test
    void given_invalid_community_id_when_delete_then_return_illegalargument_exception() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long invalidDeleteId = responseCommunities.get(responseCommunities.size() - 1).getId() + 10;

        // then
        Assertions.assertThatThrownBy(() -> deleteCommunityByAdminService
                        .deleteCommunityById(RequestId.from(invalidDeleteId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id의 커뮤니티가 존재하지 않습니다.");
    }

    @DisplayName("게시물을 삭제제한다.")
    @Test
    void given_valid_community_id_when_delete_return_delete_massage() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        ResponseCommunity responseCommunity = responseCommunities.get(responseCommunities.size() - 1);
        Long deleteId = responseCommunity.getId();
        String title = responseCommunity.getTitle();
        String expectedMessage = title + "을 삭제하였습니다.";

        // when
        String message = deleteCommunityByAdminService.deleteCommunityById(RequestId.from(deleteId));
        Community community = communityJpaRepository.findById(deleteId).get();

        // then
        assertThat(message).isEqualTo(expectedMessage);
        assertThat(community.getIsDeleted()).isEqualTo(Deleted.TRUE);
    }
}
