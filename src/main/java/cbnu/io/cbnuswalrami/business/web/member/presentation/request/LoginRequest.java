package cbnu.io.cbnuswalrami.business.web.member.presentation.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "로그인 아이디를 입력하여주세요.")
    @NotNull(message = "로그인 아이디를 입력하여주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    public LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
