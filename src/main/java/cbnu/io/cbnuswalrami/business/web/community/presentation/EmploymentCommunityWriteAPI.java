package cbnu.io.cbnuswalrami.business.web.community.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.application.service.CommunityWriteService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employment-community")
public class EmploymentCommunityWriteAPI {

    private final MemberFindUtil memberFindUtil;

    private final CommunityWriteService communityWriteService;

    @PostMapping
    public ResponseEntity<ApiResponse> writeEmploymentCommunity(
            @RequestPart @Valid RequestCommunity requestCommunity,
            @Nullable @RequestPart("file") MultipartFile file,
            HttpServletRequest request
    ) {

        Member member = memberFindUtil.findMemberByBearerToken(request);

        ResponseCommunity responseCommunity = communityWriteService.writeCommunity(
                requestCommunity,
                file,
                member,
                EMPLOYMENT
        );
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(responseCommunity));
    }
}
