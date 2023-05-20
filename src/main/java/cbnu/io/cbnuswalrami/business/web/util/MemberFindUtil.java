package cbnu.io.cbnuswalrami.business.web.util;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.core.error.CommonTypeException;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberFindUtil {

    private final MemberJpaRepository memberJpaRepository;

    @Value("${jwt.secret}")
    private String accessTokenSecret;

    public Member findMemberByAuthentication() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("MemberFindUtil username: {}", username);
        return memberJpaRepository.findByLoginId(LoginId.from(username))
                .orElseThrow(() -> CbnuException.of(CommonTypeException.INVALID_LOGIN_ID));
    }

    public Member findMemberByBearerToken(HttpServletRequest request) {
        Claims claims = Jwts.parser()
                .setSigningKey(accessTokenSecret)
                .parseClaimsJws(resolveToken(request))
                .getBody();
        String username = claims.getSubject();
        log.info("findMemberByBearerToken username: {}", username);

        return memberJpaRepository.findByLoginId(LoginId.from(username))
                .orElseThrow(() -> CbnuException.of(CommonTypeException.INVALID_LOGIN_ID));
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
