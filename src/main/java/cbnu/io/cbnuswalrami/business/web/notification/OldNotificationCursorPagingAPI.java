package cbnu.io.cbnuswalrami.business.web.notification;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.application.service.paging.OldNotificationCursorPagingService;
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
@RequestMapping("/api/notification")
public class OldNotificationCursorPagingAPI {

    private final OldNotificationCursorPagingService oldNotificationCursorPagingService;

    @GetMapping("/old/list")
    public ResponseEntity<ApiResponse> findOldNotificationByCursor(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size
    ){
        CursorResult cursorResult = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(next, size));

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }

}
