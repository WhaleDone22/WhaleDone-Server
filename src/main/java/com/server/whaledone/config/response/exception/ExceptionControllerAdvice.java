package com.server.whaledone.config.response.exception;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult commonException(Exception exception) {
        return responseService.getFailResult(CustomExceptionStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult customException(CustomException customException) {
        return responseService.getFailResult(customException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult validationException(MethodArgumentNotValidException exception) {
        return responseService.getFailResult(CustomExceptionStatus.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult forBiddenException(AccessDeniedException exception) {
        return responseService.getFailResult(CustomExceptionStatus.INVALID_AUTHORIZATION);
    }
}
