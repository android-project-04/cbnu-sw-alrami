package cbnu.io.cbnuswalrami.common.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@EnableWebSecurity
@Configuration
public class SecurityTestConfig {


    @Bean
    @Primary
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.authorizeRequests(auth -> auth
                        .antMatchers("/api/member/approval").permitAll()
                        .antMatchers("/**").permitAll()
                )
                .sessionManagement().sessionCreationPolicy(STATELESS);
        return http.build();
    }
}
