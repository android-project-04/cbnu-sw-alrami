package cbnu.io.cbnuswalrami.test.integration.login;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.LoginCommand;
import cbnu.io.cbnuswalrami.business.web.member.presentation.request.LoginRequest;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.Token;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("로그인 통합 테스트")
public class LoginIntegrationTest extends DatabaseTestBase {

    @Autowired
    private LoginCommand loginCommand;

    @Autowired
    private SignupFixture signupFixture;


    @DisplayName("로그인하면 access token과 refetsh toekn을 준다.")
    @Test
    public void given_valid_login_info_when_login_then_ok() {
        // given
        String loginId = "abcd1234";
        String password = "Abcd1234@!";
        String nickname = "히어로123";
        Member member = signupFixture.signupMember(loginId, password, nickname);
        LoginRequest loginRequest = new LoginRequest(
                loginId,
                password
        );

        // when
        Token token = loginCommand.login(loginRequest);

        // then
        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getRefreshToken()).isNotNull();
    }

    @DisplayName("비밀번호가 틀리면 예외를 준다.")
    @Test
    public void given_invalid_password_when_login_then_ex() {
        // given
        String loginId = "abcd1234";
        String password = "Abcd1234@!";
        String nickname = "히어로123";
        signupFixture.signupMember(loginId, password, nickname);
        LoginRequest loginRequest = new LoginRequest(
                loginId,
                "password@!"
        );


        // then
        CbnuException cbnuException = assertThrows(CbnuException.class, () -> loginCommand.login(loginRequest));
        assertEquals("패스워드가 일치하지 않습니다.", cbnuException.getMessage());

    }

    @DisplayName("존재하지 않는 아이디면 예외를 던진다.")
    @Test
    public void given_invalid_login_id_when_login_then_ex() {
        // given
        LoginRequest loginRequest = new LoginRequest("abcd1sdf", "Abcd1234@!");


        // then
        CbnuException cbnuException = assertThrows(CbnuException.class, () -> loginCommand.login(loginRequest));
        assertEquals("존재하지 않는 id의 유저입니다.", cbnuException.getMessage());

    }
}
