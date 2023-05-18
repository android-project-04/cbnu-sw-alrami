package cbnu.io.cbnuswalrami.business.web.member.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Approval;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.core.error.CommonTypeException;
import cbnu.io.cbnuswalrami.business.web.member.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ApprovalChangeService implements ApprovalChangeCommand {

    private final MemberJpaRepository memberJpaRepository;

    public ApprovalChangeService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    @Transactional
    public Member changeApproval(Long memberId) {

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> CbnuException.of(CommonTypeException.NOT_FOUNT_USER));

        if (member.getApproval().equals(Approval.OK)) {
            log.info("이미 승인된 유저입니다.");
        }
        member.changeApprovalToOk();

        return member;
    }
}
