package com.server.whaledone.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.server.whaledone.config.security.auth.CustomPrincipalDetails;
import com.server.whaledone.config.security.auth.CustomPrincipalDetailsService;
import com.server.whaledone.user.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private long EXPIRATION_TIME = 1000L * 60 * 60 * 10 * 24 * 10; // 10일 (1/1000초)
    private String SECRET_KEY = "temp";
    private String HEADER_STRING = "X-AUTH-TOKEN";
    private String TOKEN_PREFIX = "Bearer ";

    private final CustomPrincipalDetailsService customPrincipalDetailsService;


    // jwt 토큰 생성
    public String createToken(String email, RoleType roleType) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("role", roleType.toString())
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    // jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customPrincipalDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 이메일 추출
    public String getUserEmail(String token) {
        return JWT.decode(token).getClaim("email").asString();
    }

    // 토큰에서 회원 권한 추출
    public String getUserRole(String token) {
        return JWT.decode(token).getClaim("role").asString();
    }

    // Request의 Header에서 token 값을 가져온다
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HEADER_STRING);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        return !JWT.decode(token).getExpiresAt().before(new Date()); // 만료되는 날짜가 오늘 이전이면 이미 만료된 것
    }
}
