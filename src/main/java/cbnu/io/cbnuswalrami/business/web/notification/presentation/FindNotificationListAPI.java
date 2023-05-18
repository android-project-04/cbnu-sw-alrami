package cbnu.io.cbnuswalrami.business.web.notification.presentation;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.application.service.paging.NotificationCursorPagingService;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class FindNotificationListAPI {

    private final NotificationCursorPagingService notificationCursorPagingService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> findNotificationList(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size
    ) {
        CursorResult cursorResult = notificationCursorPagingService
                .findNotificationDtoByCursor(Cursor.from(next, size));

        return ResponseEntity.ok()
                .body(ApiResponse.of(cursorResult));
    }
}
