package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("패스워드 단위 테스트")
public class PasswordTest {


    @DisplayName("특수문자를 넣지 않은 패스워드는 예외를 날린다.")
    @Test
    public void given_invalid_password_when_create_password_then_ex() {
        // given
        String password = "aaaa1234";

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Password(password));
        String message = exception.getMessage();
        assertEquals("올바른 비밀번호를 입력해주세요.", message);
    }

    @DisplayName("특수문자를 넣지 않은 패스워드는 예외를 날린다.")
    @Test
    public void given_short_password_when_create_password_then_ex() {
        // given
        String password = "aa34!!@";

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Password(password));
        String message = exception.getMessage();
        assertEquals("올바른 비밀번호를 입력해주세요.", message);
    }

    @DisplayName("올바른 비밀번호를 입력하면 예외를 날리지 않는다.")
    @Test
    public void given_valid_password_when_create_password_then_ok() {
        // given
        String password = "Sbcd1234@!";

        // then
        assertEquals(password, new Password(password).getPassword());

    }
}
