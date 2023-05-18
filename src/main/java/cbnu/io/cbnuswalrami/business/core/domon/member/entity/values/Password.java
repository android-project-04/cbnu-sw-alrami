package cbnu.io.cbnuswalrami.business.core.domon.member.entity.values;

import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.helper.PasswordEncodeHelper;
import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Password {

    /**
     * 최소 8자리 이상, 대/소문자, 숫자 및 특수문자 각 1개 이상 포함
     * ex) Dkssudgktpdy1!
     */
    private static final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}");

    private String password;

    /**
     * @Nullary-Constructor. JPA 기본 생성자로 member 외부 패키지에서 호출하지 말 것.
     */

    private Password(String password) {
        validatePassword(password);
        String encodedPassword = PasswordEncodeHelper.encode(password);
        this.password = encodedPassword;
    }

    protected Password() {

    }


    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("패스워드를 입력해주세요.", ErrorField.of("password", password));
        }
        if (password.length() > 40) {
            throw new IllegalArgumentException("입력 가능한 패스워드 최대길이를 초과했습니다.", ErrorField.of("password", password));
        }
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("올바른 비밀번호를 입력해주세요.", ErrorField.of("password", password));
        }
    }

    public static Password from(String password) {
        return new Password(password);
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password password1)) return false;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return password;
    }
}
