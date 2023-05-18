package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Approval;
import cbnu.io.cbnuswalrami.test.helper.fixture.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원가입 승인 단위 테스트")
public class ApprovalTest {

    @DisplayName("회원을 승인 기능을 테스트한다.")
    @Test
    public void given_member_when_change_approval_then_eq() {
        // given
        Member member = MemberFixture.createMember();

        // when
        member.changeApprovalToOk();

        // then
        Assertions.assertThat(member.getApproval()).isEqualTo(Approval.OK);

    }
}
