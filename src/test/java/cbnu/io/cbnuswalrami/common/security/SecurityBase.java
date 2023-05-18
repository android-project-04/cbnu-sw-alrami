package cbnu.io.cbnuswalrami.common.security;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public abstract class SecurityBase {

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private TokenProvider tokenProvider;


    @BeforeEach
    void setUp() {
        MemberFixture.createMember();
        Member member = signupFixture.signupMember(MemberFixture.createMember());
        Authentication authentication = tokenProvider.authenticate(member.getId());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

}
