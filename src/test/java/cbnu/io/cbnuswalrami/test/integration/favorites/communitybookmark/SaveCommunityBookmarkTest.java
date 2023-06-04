package cbnu.io.cbnuswalrami.test.integration.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.SaveCommunityBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("커뮤니티 즐겨찾기 저장 통합테스트")
public class SaveCommunityBookmarkTest extends DatabaseTestBase {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private SaveCommunityBookmarkService saveCommunityBookmarkService;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private SignupFixture signupFixture;

    @DisplayName("커뮤니티 글 하나를 저장하면 저장한 글의 정보를 반환한다.")
    @Test
    void given_community_when_save_to_bookmark_then_saved_community() {
        Member member = memberFindUtil.findMemberByAuthentication();
        List<ResponseCommunity> responseCommunities = communityFixture.write15EmploymentCommunity();
        ResponseCommunity responseCommunity = responseCommunities.get(0);
        Long saveCommunityId = responseCommunity.getId();
        ResponseCommunity actual = saveCommunityBookmarkService
                .saveCommunityBookmark(member, RequestId.from(saveCommunityId));

        assertThat(actual.getTitle()).isEqualTo(responseCommunity.getTitle());
    }

    @DisplayName("해당 커뮤니티 게시물의 작성자가 아닌 사람이 저장하면 IllegalArgumentException을 던진다.")
    @Test
    void given_invalid_member_when_save_to_bookmark_then_illegalargument_exception() {
        Member member = signupFixture.signupMember(
                "ADKLa1234",
                "Abcd1234@!",
                "한글닉네임"
        );

        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long saveCommunityId = responseCommunities.get(0).getId();

        assertThatThrownBy(() -> saveCommunityBookmarkService.saveCommunityBookmark(member, RequestId.from(saveCommunityId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id의 커뮤니티 게시글이 존재하지 않거나 게시글 작성자가 아닙니다.");
    }
}
