package cbnu.io.cbnuswalrami.test.integration.stopmember;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.stopmember.application.service.PostStopMemberService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Role.STOP_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("유저 정지시키는 기능 통합 테스트")
public class PostStopMemberTest extends DatabaseTestBase {

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private PostStopMemberService postStopMemberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @DisplayName("id값을 가지고 유저를 정지하면 정지된 유저의 Role이 STOP_USER이다.")
    @Test
    void given_member_when_stop_then_role_should_be_stop_user() {
        // given
        Member member = signupFixture.signupMember(MemberFixture.createMember());

        // when
        postStopMemberService.createStopMember(RequestId.from(member.getId()));
        Member actual = memberJpaRepository.findById(member.getId()).get();

        // then
        assertThat(actual.getRole()).isEqualTo(STOP_USER);
    }

    @DisplayName("존재하지 않는 Id의 멤버를 조회하면 IllegalArgumentException을 던진다.")
    @Test
    void when_invalid_id_then_illegalargument_exception() {

        assertThatThrownBy(() -> postStopMemberService.createStopMember(RequestId.from(Long.MAX_VALUE)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 멤버으 id값입니다.");
    }
}
