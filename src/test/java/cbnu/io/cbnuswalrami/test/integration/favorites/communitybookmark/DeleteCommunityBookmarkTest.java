package cbnu.io.cbnuswalrami.test.integration.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.command.CommunityBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.DeleteCommunityBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.communitybookmark.CommunityBookmarkFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게시글 북마크 삭제 통합테스트")
class DeleteCommunityBookmarkTest extends DatabaseTestBase {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private CommunityBookmarkFixture communityBookmarkFixture;

    @Autowired
    private DeleteCommunityBookmarkService deleteCommunityBookmarkService;

    @Autowired
    private CommunityBookmarkJpaRepository communityBookmarkJpaRepository;

    @DisplayName("게시글 북마크의 북마크 주인이 아니면 IllegalArgumentException을 던진다.")
    @Test
    void given_invalid_member_when_delete_then_illegalargument_exception() {
        // given
        ResponseCommunity responseCommunity = communityBookmarkFixture
                .saveCommunityToBookmark(communityFixture.writeOneCommunity());
        Long deleteId = responseCommunity.getId();
        Member member = signupFixture.signupMember(MemberFixture.createMember());

        // then
        assertThatThrownBy(() -> deleteCommunityBookmarkService
                .deleteCommunityBookmarkById(RequestId.from(deleteId), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 유저의 북마크가 아닙니다.");

    }

    @DisplayName("게시글 북마크를 삭제하면 해당 게시글을 DB에 존재하지 않는다.")
    @Test
    void when_delete_then_empty_bookmark() {
        Member member = memberFindUtil.findMemberByAuthentication();
        ResponseCommunity responseCommunity = communityBookmarkFixture
                .saveCommunityToBookmark(communityFixture.writeOneCommunity());

        Long deleteId = responseCommunity.getId();

        // when
        deleteCommunityBookmarkService.deleteCommunityBookmarkById(RequestId.from(deleteId), member);

        // then
        assertThat(communityBookmarkJpaRepository.findAll()).hasSize(0);
    }

    @DisplayName("존재하지 않는 북마크 id는 삭제할 수 없다.")
    @Test
    void given_invalid_bookmark_id_when_delete_then_return_illegalargument_exception() {
        Member member = memberFindUtil.findMemberByAuthentication();
        ResponseCommunity responseCommunity = communityBookmarkFixture
                .saveCommunityToBookmark(communityFixture.writeOneCommunity());

        Long deleteId = responseCommunity.getId() + 1;

        // then
        assertThatThrownBy(() -> deleteCommunityBookmarkService
                .deleteCommunityBookmarkById(RequestId.from(deleteId), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id의 북마크가 존재하지 않습니다.");
    }
}
