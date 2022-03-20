package com.server.whaledone.sms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendSmsRequestDto {

    @Schema(name = "국가번호 : default = 82")
    private String countryCode;

    @Schema(name = "수신번호 : -를 제외한 숫자만 입력 가능")
    private String recipientPhoneNumber;

    @Schema(name = "메세지 내용용")
   private String content;
}
