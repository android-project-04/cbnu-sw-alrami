package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.application.service.CommunityWriteService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityWriteAPI {

    private final CommunityWriteService communityWriteService;
    private final MemberFindUtil memberFindUtil;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> writeCommunity(
            @RequestPart @Valid RequestCommunity requestCommunity,
            @Nullable @RequestPart("file") MultipartFile file,
            HttpServletRequest request
            ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);
        ResponseCommunity responseCommunity = communityWriteService.writeCommunity(
                requestCommunity,
                file,
                member,
                COMMUNITY
        );
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(responseCommunity));
    }
}
