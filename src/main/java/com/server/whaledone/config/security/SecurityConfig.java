package com.server.whaledone.config.security;

import com.server.whaledone.config.security.jwt.JwtAuthenticationFilter;
import com.server.whaledone.config.security.jwt.JwtAuthorizationFilter;
import com.server.whaledone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**", "/swagger-resources/**",
                "/swagger-ui/**", "/webjars/**", "/swagger/**","/swagger.json", "/h2-console");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/sign-in", "/api/v1/sign-up").permitAll()
                .antMatchers("/api/v1/membership")
                .access("hasRole('ROLE_MEMBERSHIP') or hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated();
    }
}
