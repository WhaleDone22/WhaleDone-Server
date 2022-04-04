package com.server.whaledone.config.response.exception;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult commonException(Exception exception) {
        log.error(String.format("InternalServerErrorException : %s", exception.getMessage()), exception);
        return responseService.getFailResult(CustomExceptionStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult customException(CustomException customException) {
        log.info(String.format("Business Exception : %s", customException.getStatus().getMessage()), customException);
        return responseService.getFailResult(customException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult validationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        String validMessage = builder.toString();

        log.info(String.format("MethodArgumentNotValidException %s", validMessage), exception);
        return responseService.getFailResult(CustomExceptionStatus.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult forBiddenException(AccessDeniedException exception) {
        return responseService.getFailResult(CustomExceptionStatus.INVALID_AUTHORIZATION);
    }
}
