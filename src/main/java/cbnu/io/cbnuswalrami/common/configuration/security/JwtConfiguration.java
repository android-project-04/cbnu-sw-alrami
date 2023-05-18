package cbnu.io.cbnuswalrami.common.configuration.security;

import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret}")
    private String accessTokenSecret;

    @Value("${jwt.access-token-valid-in-seconds}")
    private Long accessTokenValidityInSeconds;

    private final MemberJpaRepository memberJpaRepository;

    public JwtConfiguration(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    // 액세스 토큰 발급용, 리프레시 토큰 발급용은 각각 별도의 키와 유효기간을 갖는다.
    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(accessTokenSecret, accessTokenValidityInSeconds, memberJpaRepository);
    }
}
