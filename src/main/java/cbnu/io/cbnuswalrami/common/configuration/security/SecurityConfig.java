package cbnu.io.cbnuswalrami.common.configuration.security;

import cbnu.io.cbnuswalrami.business.common.filter.SecurityFilter;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.redis.RedisSessionService;
import cbnu.io.cbnuswalrami.common.configuration.security.filter.JwtAccessDeniedHandler;
import cbnu.io.cbnuswalrami.common.configuration.security.filter.JwtAuthenticationEntryFilter;
import cbnu.io.cbnuswalrami.common.configuration.security.metadatasource.FilterInvocationMetaDaTaSource;
import cbnu.io.cbnuswalrami.common.configuration.security.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final RedisSessionService redisSessionService;
    private final JwtAuthenticationEntryFilter jwtAuthenticationEntryFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final MemberJpaRepository memberJpaRepository;

    public SecurityConfig(
            TokenProvider tokenProvider,
            RedisSessionService redisSessionService,
            JwtAuthenticationEntryFilter jwtAuthenticationEntryFilter,
            JwtAccessDeniedHandler accessDeniedHandler, MemberJpaRepository memberJpaRepository
    ) {
        this.tokenProvider = tokenProvider;
        this.redisSessionService = redisSessionService;
        this.jwtAuthenticationEntryFilter = jwtAuthenticationEntryFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.memberJpaRepository = memberJpaRepository;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests(auth -> auth
                        .antMatchers("/").permitAll()
                        .antMatchers(
                                "/api/member/signup",
                                "/api/member/login"
                        ).permitAll()
                        .antMatchers("/api/admin/approval/**").hasAuthority("ADMIN")
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(
                new SecurityFilter(tokenProvider, redisSessionService),
                UsernamePasswordAuthenticationFilter.class
        );
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryFilter)
                .accessDeniedHandler(accessDeniedHandler);
        http.userDetailsService(userDetailsService());
        http.cors().configurationSource(corsConfigurationSource());
        http.authenticationProvider(tokenProvider);

        http.addFilterAfter(getFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService(memberJpaRepository);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean(name = "filterSecurityInterceptor")
    public FilterSecurityInterceptor getFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setAccessDecisionManager(affirmativeBased());
        interceptor.setSecurityMetadataSource(getReloadableFilterInvocationSecurityMetadataSource());

        return interceptor;
    }

    @Bean(name = "filterInvocationSecurityMetadataSource")
    public FilterInvocationSecurityMetadataSource getReloadableFilterInvocationSecurityMetadataSource() {
        return new FilterInvocationMetaDaTaSource();
    }

    @Bean
    public AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        return affirmativeBased;
    }


    @Bean
    public List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }


    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > NORMAL");
        return roleHierarchy;
    }
}
