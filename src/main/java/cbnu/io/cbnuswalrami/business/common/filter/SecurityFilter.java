package cbnu.io.cbnuswalrami.business.common.filter;

import cbnu.io.cbnuswalrami.business.web.redis.RedisSessionService;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
public class SecurityFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SESSION_ID = "REFRESH";
    private final TokenProvider tokenProvider;
    public static final String AUTHORITY = "AUTHORITY";
    private final RedisSessionService redisSessionService;

    public SecurityFilter(TokenProvider tokenProvider, RedisSessionService redisSessionService) {
        this.tokenProvider = tokenProvider;
        this.redisSessionService = redisSessionService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        String sessionId = getSessionId(request);

        //유효성 검증 - authentication access token으로 검증
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            log.info("access token 검증 시작");
            // 토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 Authentication 반환
            createAuthFromAccessToken(httpServletResponse, jwt, requestURI);
            chain.doFilter(request, response);
        }

        //유효성 검증 refresh token으로 검증
        if (sessionId != null && redisSessionService.getMemberId(sessionId) != null) {
            log.info("refresh toekn 검증 시작");
            createAuthFromRefreshToken(httpServletResponse, sessionId);
            chain.doFilter(request, response);
        }


        chain.doFilter(request, response);
    }

    private void createAuthFromAccessToken(HttpServletResponse httpServletResponse, String jwt, String requestURI) {
        Authentication authentication = tokenProvider.authenticate(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        httpServletResponse.setHeader(AUTHORIZATION_HEADER, token);
        httpServletResponse.setHeader(AUTHORITY, authentication.getAuthorities().toString());
    }

    private void createAuthFromRefreshToken(HttpServletResponse httpServletResponse, String sessionId) {
        Long usersId = redisSessionService.getMemberId(sessionId);
        Authentication authenticate = tokenProvider.authenticate(usersId);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = tokenProvider.createToken(authenticate);
        httpServletResponse.setHeader(AUTHORIZATION_HEADER, token);
        httpServletResponse.setHeader(AUTHORITY, authenticate.getAuthorities().toString());
        redisSessionService.deleteSession(usersId, sessionId);
        redisSessionService.save(usersId);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("token = " + bearerToken.substring(0));
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getSessionId(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie sessionCookie = Arrays.stream(httpServletRequest.getCookies())
                .filter(hasSessionId())
                .findAny()
                .orElseGet(() -> null);
        return sessionCookie == null ? null : sessionCookie.getValue();
    }

    private Predicate<Cookie> hasSessionId() {
        return cookie -> cookie.getName().equals(SESSION_ID);
    }
}
