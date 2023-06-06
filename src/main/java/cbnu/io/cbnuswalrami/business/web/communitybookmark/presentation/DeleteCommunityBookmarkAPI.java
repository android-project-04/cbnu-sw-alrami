package cbnu.io.cbnuswalrami.business.web.communitybookmark.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.DeleteCommunityBookmarkService;
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
@RequestMapping("/api/community-bookmark")
public class DeleteCommunityBookmarkAPI {

    private final MemberFindUtil memberFindUtil;

    private final DeleteCommunityBookmarkService deleteCommunityBookmarkService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBookmarkById(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        String message = deleteCommunityBookmarkService.deleteCommunityBookmarkById(RequestId.from(id), member);
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(message));
    }
}
