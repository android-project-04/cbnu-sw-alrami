package cbnu.io.cbnuswalrami.business.web.user.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.query.MemberQueryRepository;
import cbnu.io.cbnuswalrami.business.core.error.CommonTypeException;
import cbnu.io.cbnuswalrami.business.web.redis.RedisSessionService;
import cbnu.io.cbnuswalrami.business.web.user.application.LoginCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.LoginRequest;
import cbnu.io.cbnuswalrami.business.web.user.presentation.response.Token;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LoginService implements LoginCommand {

    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisSessionService redisSessionService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public LoginService(
            MemberQueryRepository memberQueryRepository,
            PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider,
            RedisSessionService redisSessionService,
            AuthenticationManagerBuilder authenticationManagerBuilder
    ) {
        this.memberQueryRepository = memberQueryRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.redisSessionService = redisSessionService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public Token login(LoginRequest loginRequest) {
        Optional<Member> optionalUsers = memberQueryRepository.findValidLoginUserByLoginId(LoginId.from(loginRequest.getLoginId()));
        if (optionalUsers.isEmpty()) {
            throw CbnuException.of(CommonTypeException.NOT_FOUNT_USER);
        }
        Member member = optionalUsers.get();
        matchPassword(loginRequest.getPassword(), member.getPassword().getPassword());

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());
        authorities.add(simpleGrantedAuthority);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword(), authorities);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = redisSessionService.save(member.getId());

        return new Token(
                accessToken,
                refreshToken,
                member.getRole());
    }

    private void matchPassword(String password, String encodedPassword) {
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        log.info("==============password match start==============");
        if (!matches) {
            log.info("패스워드가 일치하지 않습니다.");
            throw CbnuException.of(CommonTypeException.INVALID_PASSWORD);
        }
    }
}
