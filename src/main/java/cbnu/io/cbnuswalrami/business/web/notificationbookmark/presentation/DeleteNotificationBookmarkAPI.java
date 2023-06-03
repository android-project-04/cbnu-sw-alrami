package cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.DeleteNotificationBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification-bookmark")
public class DeleteNotificationBookmarkAPI {

    private final DeleteNotificationBookmarkService deleteNotificationBookmarkService;
    private final MemberFindUtil memberFindUtil;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNotificationBookmarkById(
            @PathVariable("id") Long deleteId,
            HttpServletRequest request
    ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        deleteNotificationBookmarkService.deleteNotificationBookmark(member, RequestId.from(deleteId));

        String deleteMessage = "id: " + deleteId + "가 잘 삭제가 되었습니다.";

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(deleteMessage));
    }
}
