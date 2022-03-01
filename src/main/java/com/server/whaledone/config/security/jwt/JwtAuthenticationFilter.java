package com.server.whaledone.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.whaledone.config.security.auth.CustomPrincipalDetails;
import com.server.whaledone.user.dto.request.SignInRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter() {
        super();
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter 진입");

        // request의 email과 password를 파싱해서 저장
        ObjectMapper signInMapper = new ObjectMapper();
        SignInRequestDto signInRequestDto = null;
        try{
            signInRequestDto = signInMapper.readValue(request.getInputStream(), SignInRequestDto.class);
        } catch (Exception e) {
            System.out.println("attemptAuthentication = " + e);
            e.printStackTrace();
        }

        // 인증을 하기 위해 입력값으로 토큰 생성
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(
                    signInRequestDto.getEmail(),
                    signInRequestDto.getPassword());

        // 해당 토큰으로 검증을 진행한다.
        //
        Authentication authentication = authenticationManager.authenticate(authToken);

        CustomPrincipalDetails principal = (CustomPrincipalDetails) authentication.getPrincipal();
        System.out.println("principal = " + principal);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomPrincipalDetails userDetails = (CustomPrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(userDetails.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userDetails.getUser().getId())
                .withClaim("email", userDetails.getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));

        response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
