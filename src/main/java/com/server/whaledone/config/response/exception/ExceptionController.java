package com.server.whaledone.config.response.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/expired-token")
    public void getExpiredTokenException() {
        throw new CustomException(CustomExceptionStatus.TOKEN_EXPIRED);
    }

    @GetMapping("/invalid-token")
    public void getInvalidTokenException() {
        throw new CustomException(CustomExceptionStatus.INVALID_TOKEN);
    }

    @GetMapping("/jwt")
    public void getJwtException() {
        throw new CustomException(CustomExceptionStatus.INVALID_AUTHENTICATION);
    }
}
