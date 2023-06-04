package cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.paging.NotificationBookmarkCursorPagingService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification-bookmark")
public class NotificationCursorPagingAPI {

    private final NotificationBookmarkCursorPagingService notificationBookmarkCursorPagingService;

    private final MemberFindUtil memberFindUtil;

    @GetMapping
    public ResponseEntity<ApiResponse> findNotificationBookmarkByCursorPaging(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size,
            HttpServletRequest request
            ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        CursorResult cursorResult = notificationBookmarkCursorPagingService.findNotificationBookmarksByCursor(
                Cursor.from(next, size),
                member
        );

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }
}
