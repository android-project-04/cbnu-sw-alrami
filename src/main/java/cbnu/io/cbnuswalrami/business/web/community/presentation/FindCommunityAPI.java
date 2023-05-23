package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.application.service.FindCommunityService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class FindCommunityAPI {

    private final FindCommunityService communityService;
    private final MemberFindUtil memberFindUtil;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findCommunityById(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);

        ResponseCommunity responseCommunity = communityService.findCommunityById(RequestId.from(id), RequestId.from(member.getId()));
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(responseCommunity));
    }
}
