package cbnu.io.cbnuswalrami.business.web.user.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
