package com.server.whaledone.sms.dto.standard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MessageDto {
    private String to;
    private String content;

    @Builder
    public MessageDto(String to, String content) {
        this.to = to;
        this.content = content;
    }
}
