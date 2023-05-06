package cbnu.io.cbnuswalrami.business.web.user.presentation;

import cbnu.io.cbnuswalrami.business.common.filter.SecurityFilter;
import cbnu.io.cbnuswalrami.business.web.user.application.service.LoginService;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.LoginRequest;
import cbnu.io.cbnuswalrami.business.web.user.presentation.response.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/member")
public class LoginAPI {

    private final LoginService loginService;

    public LoginAPI(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest) {
        Token token = loginService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(SecurityFilter.AUTHORIZATION_HEADER, token.getAccessToken())
                .header(SecurityFilter.SESSION_ID, token.getRefreshToken())
                .header(SecurityFilter.AUTHORITY, token.getRole().toString())
                .build();
    }
}
