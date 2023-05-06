package cbnu.io.cbnuswalrami.business.web.user.presentation.response;


import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Role;
import lombok.Getter;

@Getter
public class Token {

    private String accessToken;

    private String refreshToken;

    private Role role;

    public Token(String accessToken, String refreshToken, Role role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
