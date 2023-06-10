package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.application.service.DeleteCommunityByAdminService;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/community")
public class DeleteCommunityByAdminAPI {

    private final DeleteCommunityByAdminService deleteCommunityByAdminService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCommunityByAdmin(@PathVariable("id") Long id) {
        String message = deleteCommunityByAdminService.deleteCommunityById(RequestId.from(id));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(message));
    }
}
