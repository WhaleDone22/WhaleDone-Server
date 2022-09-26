package com.server.whaledone.certification.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "SmsCode", timeToLive = 180)
@AllArgsConstructor
@Builder
public class SmsCode {

    @Id
    private String phoneNumber;

    @Indexed
    private String code;

    public static SmsCode of(String phoneNumber, String code) {
        return SmsCode.builder()
                .phoneNumber(phoneNumber)
                .code(code)
                .build();
    }
}
