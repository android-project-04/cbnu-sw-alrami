package cbnu.io.cbnuswalrami.business.web.member.application;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;

public interface ApprovalChangeCommand {
    Member changeApproval(Long memberId);
}
