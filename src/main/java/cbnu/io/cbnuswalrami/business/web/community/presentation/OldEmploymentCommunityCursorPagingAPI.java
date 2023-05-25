package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.application.service.paging.OldCommunityCursorPagingService;
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
@RequestMapping("/api/employment-community")
public class OldEmploymentCommunityCursorPagingAPI {

    private final OldCommunityCursorPagingService oldCommunityCursorPagingService;

    @GetMapping("/old/cursor")
    public ResponseEntity<ApiResponse> findEmploymentCommunitiesByCursor(
            @RequestParam @Nullable Long next,
            @RequestParam @Nullable Long size
    ) {
        CursorResult cursorResult = oldCommunityCursorPagingService.findEmploymentCommunities(Cursor.from(next, size));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(cursorResult));
    }
}
