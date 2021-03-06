package com.server.whaledone.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetailsService;
import com.server.whaledone.user.entity.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private long EXPIRATION_TIME = 100 * 24 * 60 * 60 * 1000L;
    private String HEADER_STRING = "X-AUTH-TOKEN";
    private String TOKEN_PREFIX = "Bearer ";

    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    private void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    // jwt 토큰 생성
    public String createToken(String email, RoleType roleType) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("role", roleType.toString())
                .withClaim("email", email)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUserEmail(token));
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
    public boolean validateToken(String token, ServletRequest request) {
        if (token == null) {
            return false;
        }
        try {
            final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (TokenExpiredException tokenExpiredException) {
            request.setAttribute("tokenException", CustomExceptionStatus.TOKEN_EXPIRED);
        } catch (AlgorithmMismatchException | InvalidClaimException invalidTokenException) {
            request.setAttribute("tokenException", CustomExceptionStatus.INVALID_TOKEN);
        } catch (JWTVerificationException exception) {
            request.setAttribute("tokenException", CustomExceptionStatus.INVALID_AUTHENTICATION);
        }
        return false;
    }
}
