package cbnu.io.cbnuswalrami.test.integration.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.service.FindMyPageService;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.ResponseMyPage;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마이페이지 조회 통합테스트")
public class FindMyPageTest extends DatabaseTestBase {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private FindMyPageService findMyPageService;

    @DisplayName("커뮤니티의 글을 15개 쓴 유저는 responseMyPageCommunities의 size가 15이다.")
    @Test
    void given_15_community_when_find_mypage_then_15_communities() {
        Member member = memberFindUtil.findMemberByAuthentication();

        communityFixture.write15Community(member);

        ResponseMyPage myPage = findMyPageService.findMyPage(member);

        assertThat(myPage.getResponseMyPageCommunities()).hasSize(15);
    }

    @DisplayName("커뮤니티의 글을 0개 쓴 유저는 responseMyPageCommunities의 size가 0이다.")
    @Test
    void given_0_community_when_find_mypage_then_0_community() {
        Member member = memberFindUtil.findMemberByAuthentication();

        ResponseMyPage myPage = findMyPageService.findMyPage(member);

        assertThat(myPage.getResponseMyPageCommunities()).hasSize(0);
    }
}
