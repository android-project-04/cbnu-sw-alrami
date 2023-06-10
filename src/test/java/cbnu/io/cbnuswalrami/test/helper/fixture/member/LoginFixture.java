package cbnu.io.cbnuswalrami.test.helper.fixture.member;


import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginFixture {

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private TokenProvider tokenProvider;

    public void LoginAdmin() {
        Member member = signupFixture.signupAdmin();
        Authentication authentication = tokenProvider.authenticate(member.getId());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
