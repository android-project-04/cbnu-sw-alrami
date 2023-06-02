package cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.SaveNotificationService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification-bookmark")
public class SaveNotificationBookmarkAPI {

    private final MemberFindUtil memberFindUtil;

    private final SaveNotificationService saveNotificationService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> saveNotificationBookmark(
            @PathVariable("id") Long notificationId,
            HttpServletRequest request
    ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        NotificationDto notificationDto = saveNotificationService.saveNotificationBookmark(RequestId.from(notificationId), member);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(notificationDto));
    }
}
