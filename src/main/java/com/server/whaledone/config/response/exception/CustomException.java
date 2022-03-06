package com.server.whaledone.config.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private CustomExceptionStatus status;

}
