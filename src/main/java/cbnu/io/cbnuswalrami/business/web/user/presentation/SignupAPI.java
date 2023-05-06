package cbnu.io.cbnuswalrami.business.web.user.presentation;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import cbnu.io.cbnuswalrami.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
public class SignupAPI {

    private final SignupCommand signupCommand;

    public SignupAPI(SignupCommand signupCommand) {
        this.signupCommand = signupCommand;
    }

    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> signup(
            @RequestPart @Valid SignupRequest request,
            @RequestPart("file")MultipartFile file

            ) {
        // 회원가입
        Member signup = signupCommand.signup(request, file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(signup.getId(), HttpStatus.CREATED));
    }
}
