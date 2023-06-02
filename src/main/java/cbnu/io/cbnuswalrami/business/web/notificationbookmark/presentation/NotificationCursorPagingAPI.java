package cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.paging.NotificationBookmarkCursorPagingService;
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
@RequestMapping("/api/notification-bookmark")
public class NotificationCursorPagingAPI {

    private final NotificationBookmarkCursorPagingService notificationBookmarkCursorPagingService;

    @GetMapping
    public ResponseEntity<ApiResponse> findNotificationBookmarkByCursorPaging(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size
            ) {
        CursorResult cursorResult = notificationBookmarkCursorPagingService.findNotificationBookmarksByCursor(Cursor.from(next, size));

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }
}
