package com.server.whaledone.sms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendSmsRequestDto {

    @Schema(description = "국가번호", example = "82")
    private String countryCode;

    @Schema(description = "수신번호", example = "01012345678 (- 제외)")
    private String recipientPhoneNumber;
}
