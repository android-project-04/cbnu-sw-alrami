package cbnu.io.cbnuswalrami.test.integration.approval;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.member.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Approval.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("멤버 승인 통합 테스트")
public class ApprovalIntegrationTest extends DatabaseTestBase {

    @Autowired
    private ApprovalChangeCommand approvalChangeCommand;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @DisplayName("비승인 회원을 승인 회원으로 만든다.")
    @Test
    public void given_no_approval_when_change_approval_then_ok_approval() {
        // given
        Member member = MemberFixture.createMember();
        Member savedMember = memberJpaRepository.save(member);

        // when
        Member testMember = approvalChangeCommand.changeApproval(savedMember.getId());

        // then
        assertThat(testMember.getApproval()).isEqualTo(OK);

    }
}
