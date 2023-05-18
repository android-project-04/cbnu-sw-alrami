package cbnu.io.cbnuswalrami.business.web.member.application;

import cbnu.io.cbnuswalrami.business.web.member.presentation.request.LoginRequest;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.Token;

public interface LoginCommand {

    public Token login(LoginRequest loginRequest);
}
