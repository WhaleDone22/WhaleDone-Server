package com.server.whaledone.sms.dto.standard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SmsRequestDto {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<MessageDto> messages;

    @Builder
    public SmsRequestDto(String type, String contentType, String countryCode, String from, String content, List<MessageDto> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }
}
