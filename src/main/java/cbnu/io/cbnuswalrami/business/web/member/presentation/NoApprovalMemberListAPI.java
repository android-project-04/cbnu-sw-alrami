package cbnu.io.cbnuswalrami.business.web.member.presentation;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.member.application.service.paging.ApprovalCursorPagingService;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class NoApprovalMemberListAPI {

    private final ApprovalCursorPagingService approvalCursorPagingService;

    @GetMapping("/no/approval/members")
    public ResponseEntity<ApiResponse> findNoApprovalList(@RequestParam @Nullable Long next, @RequestParam @Nullable Long size) {
        CursorResult cursorResult = approvalCursorPagingService.findMemberDtosByCursor(Cursor.from(next, size));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }
}
