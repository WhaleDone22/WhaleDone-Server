package com.server.whaledone.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailRequestDto {

    @Schema(description = "메일 받을 주소")
    private String email;

    @Schema(description = "메일 내용")
    private String message;

    @Builder
    public MailRequestDto(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
