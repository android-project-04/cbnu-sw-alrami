package cbnu.io.cbnuswalrami.business.web.user.application;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;

public interface ApprovalChangeCommand {
    Member changeApproval(Long memberId);
}
