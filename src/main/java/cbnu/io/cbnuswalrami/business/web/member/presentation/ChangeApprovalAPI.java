package cbnu.io.cbnuswalrami.business.web.member.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class ChangeApprovalAPI {

    private final ApprovalChangeCommand approvalChangeCommand;

    public ChangeApprovalAPI(ApprovalChangeCommand approvalChangeCommand) {
        this.approvalChangeCommand = approvalChangeCommand;
    }


//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/approval/{id}")
    public ResponseEntity<ApiResponse> changeMemberApproval(@PathVariable Long id) {
        Member member = approvalChangeCommand.changeApproval(id);
        return ResponseEntity.ok()
                .body(ApiResponse.of(member.getId()));
    }
}
