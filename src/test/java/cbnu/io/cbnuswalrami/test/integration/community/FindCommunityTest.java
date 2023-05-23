package cbnu.io.cbnuswalrami.test.integration.community;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.application.service.FindCommunityService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.web.util.RedisKey.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("id값으로 커뮤니티 조회 통합테스트")
public class FindCommunityTest extends DatabaseTestBase {

    @Autowired
    private FindCommunityService findCommunityService;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @DisplayName("id값으로 조회하면 하나의 게시글을 반환한다.")
    @Test

    void given_id_when_find_community_then_return_community() {
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long id = responseCommunities.get(2).getId();
        Member member = signupFixture.signupMember(MemberFixture.createMember());
        ResponseCommunity responseCommunity = findCommunityService.findCommunityById(RequestId.from(id), RequestId.from(member.getId()));

        assertThat(responseCommunity.getId()).isEqualTo(id);
    }

    @DisplayName("존재하지 않는 id값으로 조회하면 IllegalArgumentException을 던진다.")
    @Test
    void given_invalid_id_when_find_community_then_illegalargument_exception() {
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long id = responseCommunities.get(responseCommunities.size() - 1).getId();
        id += 100L;
        Member member = signupFixture.signupMember(MemberFixture.createMember());

        Long finalId = id;
        assertThatThrownBy(() -> findCommunityService.findCommunityById(RequestId.from(finalId), RequestId.from(member.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id값의 커뮨니티 게시글이 존재하지 않습니다.");
    }

    @DisplayName("한번 조회하면 redis에 INCR 함수를 사용하여 조회수를 증가한다")
    @Test
    void given_valid_id_when_find_community_then_redis_count_1() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long findId = responseCommunities.get(responseCommunities.size() - 1).getId();

        Member member = signupFixture.signupMember(MemberFixture.createMember());

        // when
        findCommunityService.findCommunityById(RequestId.from(findId), RequestId.from(member.getId()));
        String countString = redisTemplate.opsForValue().get(COMMUNITY_COUNT_KEY + findId);
        int count = Integer.parseInt(countString);

        // then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("같은 사용자가 두번 조회하여도 조회수는 하나만 증가한다.")
    @Test
    void given_valid_id_when_find_two_community_then_redis_count_1() {
        // given
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        Long findId = responseCommunities.get(responseCommunities.size() - 1).getId();

        Member member = signupFixture.signupMember(MemberFixture.createMember());

        // when
        findCommunityService.findCommunityById(RequestId.from(findId), RequestId.from(member.getId())); // 한 번 조회
        findCommunityService.findCommunityById(RequestId.from(findId), RequestId.from(member.getId())); // 두 번 조회
        String countString = redisTemplate.opsForValue().get(COMMUNITY_COUNT_KEY + findId);
        int count = Integer.parseInt(countString);

        // then
        assertThat(count).isEqualTo(1);
    }
}
