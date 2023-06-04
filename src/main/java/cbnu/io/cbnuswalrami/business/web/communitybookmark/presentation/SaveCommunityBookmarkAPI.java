package cbnu.io.cbnuswalrami.business.web.communitybookmark.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.SaveCommunityBookmarkService;
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
@RequestMapping("/api/community-bookmark")
public class SaveCommunityBookmarkAPI {

    private final MemberFindUtil memberFindUtil;

    private final SaveCommunityBookmarkService saveCommunityBookmarkService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> saveCommunityBookmark(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        ResponseCommunity responseCommunity = saveCommunityBookmarkService
                .saveCommunityBookmark(member, RequestId.from(id));

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(responseCommunity));
    }
}
