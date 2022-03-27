package com.server.whaledone.sms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ValidateSmsCodeRequestDto {
    @NotBlank
    @Schema(description = "인증 번호", example = "5jdks")
    private String smsCode;

    @NotBlank
    @Schema(description = "인증하는 회원 전화번호", example = "01012345678")
    private String phoneNumber;
}
