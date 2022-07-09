package com.server.whaledone.config.response.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CustomExceptionStatus exception = (CustomExceptionStatus) request.getAttribute("tokenException");
        log.error("Authentication Exception at AuthenticationEntryPoint");

        if (exception == null || exception.equals(CustomExceptionStatus.INVALID_TOKEN)){
            response.sendRedirect("/exception/invalid-token"); // AlgorithmMismatchException, InvalidClaimException, NullPointerException
        } else if (exception.equals(CustomExceptionStatus.TOKEN_EXPIRED)) {
            response.sendRedirect("/exception/expired-token"); // TokenExpiredException
        } else if (exception.equals(CustomExceptionStatus.INVALID_AUTHENTICATION)) {
            response.sendRedirect(("/exception/jwt"));// JWTVerificationException
        }
    }
}
