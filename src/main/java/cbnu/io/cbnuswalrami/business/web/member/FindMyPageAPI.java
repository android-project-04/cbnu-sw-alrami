package cbnu.io.cbnuswalrami.business.web.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.service.FindMyPageService;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.ResponseMyPage;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/mypage")
public class FindMyPageAPI {

    private final MemberFindUtil memberFindUtil;
    private final FindMyPageService findMyPageService;

    @GetMapping
    public ResponseEntity<ApiResponse> findMyPage(HttpServletRequest request) {

        Member member = memberFindUtil.findMemberByBearerToken(request);
        ResponseMyPage myPage = findMyPageService.findMyPage(member);
        return ResponseEntity
                .ok()
                .body(ApiResponse.of(myPage));
    }
}
