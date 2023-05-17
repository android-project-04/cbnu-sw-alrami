package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("로그인 아이디 단위 테스트")
class LoginIdTest {

    @DisplayName("15글자 이상의 아이디를 작성하면 예외를 날린다.")
    @Test
    public void given_long_login_id_when_create_login_id_then_ex() {
        // given
        String loginId = "123456789123456789";

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> LoginId.from(loginId));
        String message = exception.getMessage();
        assertEquals("입력 가능한 아이디 최대길이를 초과했습니다.", message);
    }

    @DisplayName("null값을 넣으면 예외를 날린다.")
    @Test
    public void given_null_login_id_when_create_login_id_then_ex() {
        // given
        String loginId = null;

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> LoginId.from(loginId));
        String message = exception.getMessage();
        assertEquals("아이디를 입력해주세요.", message);
    }
}
