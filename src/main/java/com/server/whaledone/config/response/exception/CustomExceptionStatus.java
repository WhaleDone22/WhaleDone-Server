package com.server.whaledone.config.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    INVALID_INPUT_VALUE(true, 400, "유효하지 않은 입력입니다.");

    private final boolean responseStatus;
    private final int code;
    private final String message;


}
