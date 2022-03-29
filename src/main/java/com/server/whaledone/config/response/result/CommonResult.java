package com.server.whaledone.config.response.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @Schema(description = "응답 성공여부", example = "true / false")
    private boolean responseSuccess;

    @Schema(description = "응답 코드 번호", example = "C001")
    private String code;

    @Schema(description = "응답 메세지")
    private String message;
}
