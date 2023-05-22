package cbnu.io.cbnuswalrami.business.web.util;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.core.error.CommonTypeException;
import cbnu.io.cbnuswalrami.business.web.redis.RedisSessionService;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFindUtil {
    public static final String SESSION_ID = "REFRESH";
    private final MemberJpaRepository memberJpaRepository;

    private final RedisSessionService redisSessionService;

    @Value("${jwt.secret}")
    private String accessTokenSecret;

    public Member findMemberByAuthentication() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("MemberFindUtil username: {}", username);
        return memberJpaRepository.findByLoginId(LoginId.from(username))
                .orElseThrow(() -> CbnuException.of(CommonTypeException.INVALID_LOGIN_ID));
    }

    public Member findMemberByBearerToken(HttpServletRequest request) {
        String username = null;
        String token = resolveToken(request);
        if (token != null && !token.isBlank()) {
            Claims claims = Jwts.parser()
                    .setSigningKey(accessTokenSecret)
                    .parseClaimsJws(resolveToken(request))
                    .getBody();
            username = claims.getSubject();
            log.info("findMemberByBearerToken username: {}", username);
            return memberJpaRepository.findByLoginId(LoginId.from(username))
                    .orElseThrow(() -> CbnuException.of(CommonTypeException.INVALID_LOGIN_ID));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return memberJpaRepository.findByLoginId(LoginId.from(name))
                .orElseThrow(() -> CbnuException.of(CommonTypeException.NOT_FOUNT_USER));
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("token = " + bearerToken.substring(0));
            return bearerToken.substring(7);
        }
        return null;
    }

}
