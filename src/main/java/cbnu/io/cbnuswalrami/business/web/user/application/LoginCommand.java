package cbnu.io.cbnuswalrami.business.web.user.application;

import cbnu.io.cbnuswalrami.business.web.user.presentation.request.LoginRequest;
import cbnu.io.cbnuswalrami.business.web.user.presentation.response.Token;

public interface LoginCommand {

    public Token login(LoginRequest loginRequest);
}
