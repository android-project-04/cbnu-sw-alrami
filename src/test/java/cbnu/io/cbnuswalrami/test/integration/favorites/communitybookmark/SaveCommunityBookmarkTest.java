package cbnu.io.cbnuswalrami.test.integration.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.SaveCommunityBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("커뮤니티 즐겨찾기 저장 통합테스트")
class SaveCommunityBookmarkTest extends DatabaseTestBase {

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
}
