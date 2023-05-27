package cbnu.io.cbnuswalrami.business.web.member.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.service.ChangeNicknameService;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.MemberDto;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/nickname")
public class ChangeNicknameAPI {

    private final MemberFindUtil memberFindUtil;

    private final ChangeNicknameService changeNicknameService;

    @PutMapping
    public ResponseEntity<ApiResponse> changeNickname(
            @RequestParam(value = "nickname") String nickname,
            HttpServletRequest request
            ) {
        Member member = memberFindUtil.findMemberByBearerToken(request);

        MemberDto memberDto = changeNicknameService.changeNickname(nickname, member);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(memberDto));
    }
}
