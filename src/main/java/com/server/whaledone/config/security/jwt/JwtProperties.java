package com.server.whaledone.config.security.jwt;

public interface JwtProperties {
    String SECRET_KEY = "temp"; // 우리 서버만 알고 있는 비밀값
    long EXPIRATION_TIME = 1000L * 60 * 60 * 10 * 24 * 10; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
