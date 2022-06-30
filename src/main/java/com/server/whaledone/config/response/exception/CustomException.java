package com.server.whaledone.config.response.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private CustomExceptionStatus status;

    public CustomException(CustomExceptionStatus status) {
        super(status.toString() +" : " + status.getMessage());
        this.status = status;
    }
}
