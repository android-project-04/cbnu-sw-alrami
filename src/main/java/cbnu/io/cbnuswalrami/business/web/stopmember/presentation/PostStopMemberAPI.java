package cbnu.io.cbnuswalrami.business.web.stopmember.presentation;

import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.stopmember.application.service.PostStopMemberService;
import cbnu.io.cbnuswalrami.business.web.stopmember.presentation.response.ResponseStopMember;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/stop-member")
public class PostStopMemberAPI {

    private final PostStopMemberService postStopMemberService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> createStopMember(@PathVariable("id") Long id) {

        ResponseStopMember stopMember = postStopMemberService.createStopMember(RequestId.from(id));

        return ResponseEntity
                .ok()
                .body(ApiResponse.of(stopMember));
    }
}
