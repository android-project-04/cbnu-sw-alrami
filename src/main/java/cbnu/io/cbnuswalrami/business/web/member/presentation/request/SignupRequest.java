package cbnu.io.cbnuswalrami.business.web.member.presentation.request;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class SignupRequest {

    @NotNull(message = "로그인 아이디를 입력하여 주세요.")
    @NotEmpty(message = "로그인 아이디를 입력하여 주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력하여 주세요")
    @NotEmpty(message = "비밀번호를 입력하여 주세요")
    private String password;

    @NotNull(message = "학번을 비우면 안됩니다.")
    private Integer studentNumber;

    private String nickname;

    public SignupRequest(String loginId, String password, String nickname, Integer studentNumber) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.studentNumber = studentNumber;
    }



    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return String.format("LoginId: [%s], StudentNumber: [%s]", loginId, studentNumber);
    }
}
