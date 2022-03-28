package com.server.whaledone.sms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendSmsRequestDto {

    @Schema(description = "국가번호", example = "82")
    private String countryCode;

    @Schema(description = "SMS 메세지를 받을 수신번호", example = "01012345678 (- 제외)")
    private String recipientPhoneNumber;

    @Schema(description = "SMS 요청 타입(회원가입 시 국가 인증과 비밀번호 찾기에 쓰인다)", example = "SIGNUP, PW")
    private SmsType smsType;
}
