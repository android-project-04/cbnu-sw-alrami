package cbnu.io.cbnuswalrami.test.unit.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Role.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member 단위테스트")
class MemberTest {

    @DisplayName("멤버의 권한을 STOP_USER로 잘 바뀌는지 테스트한다.")
    @Test
    void when_change_role_to_stop_user_then_member_role_should_be_stop_user() {
        Member member = MemberFixture.createMember();
        member.changeRole(STOP_USER);

        assertThat(member.getRole()).isEqualTo(STOP_USER);
    }

    @DisplayName("멤버의 닉네임을 변경한다.")
    @Test
    void when_change_nickname_then_changed_nickname() {
        // given
        String changeNickname = "한글 닉네임😀";
        Member member = MemberFixture.createMember();

        // when
        member.changeNickname(changeNickname);
        assertThat(member.getNickname().getNickname()).isEqualTo(changeNickname);
    }
}
