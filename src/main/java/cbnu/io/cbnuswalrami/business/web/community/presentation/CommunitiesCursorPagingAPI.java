package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.application.service.paging.CommunityCursorPagingService;
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
@RequestMapping("/api/community")
public class CommunitiesCursorPagingAPI {

    private final CommunityCursorPagingService communityCursorPagingService;

    @GetMapping("/cursor")
    public ResponseEntity<ApiResponse> findCommunitiesByCursor(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size
    ) {
        CursorResult cursorResult = communityCursorPagingService.findByCursor(Cursor.from(next, size));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }
}
