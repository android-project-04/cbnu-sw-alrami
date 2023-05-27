package cbnu.io.cbnuswalrami.business.core.domon.member.entity.values;

import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Nickname {

    private static final Pattern pattern = Pattern.compile("^(?=.*[\\p{IsHangul}\\p{IsAlphabetic}]).{3,39}$");

    private String nickname;

    /**
     * @Nullary-Constructor JPA 기본 생성자로 member 외부 패키지에서 호출하지 말 것.
     */
    protected Nickname() {

    }

    private Nickname(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(String nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.", ErrorField.of("Nickname", nickname));
        }
        if (nickname.length() > 39) {
            throw new IllegalArgumentException("입력 가능한 닉네임의 최대길이를 초과했습니다.", ErrorField.of("Nickname", nickname));
        }
        if (!pattern.matcher(nickname).matches()) {
            throw new IllegalArgumentException("올바른 양식의 닉네임을 입력해주세요.", ErrorField.of("Nickname", nickname));
        }
    }

    public static Nickname from(String nickname) {
        return new Nickname(nickname);
    }
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nickname nickName)) return false;
        return getNickname().equals(nickName.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname());
    }

    @Override
    public String toString() {
        return nickname;
    }
}
