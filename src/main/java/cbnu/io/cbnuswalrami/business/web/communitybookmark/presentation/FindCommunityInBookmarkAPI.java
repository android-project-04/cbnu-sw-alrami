package cbnu.io.cbnuswalrami.business.web.communitybookmark.presentation;

import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.FindCommunityInBookmarkService;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community-bookmark")
public class FindCommunityInBookmarkAPI {

    private final FindCommunityInBookmarkService findCommunityInBookmarkService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findCommunityByBookmarkId(@PathVariable("id") Long id) {
        ResponseCommunity responseCommunity = findCommunityInBookmarkService
                .findCommunityByBookmarkId(RequestId.from(id));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(responseCommunity));
    }
}
