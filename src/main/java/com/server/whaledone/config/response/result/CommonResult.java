package com.server.whaledone.config.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공여부 : ex) true / false")
    private boolean responseSuccess;

    @ApiModelProperty(value = "응답 코드 번호 : ex) 200(ok), 404(not found)")
    private int code;

    @ApiModelProperty(value = "응답 메세지")
    private String message;
}
