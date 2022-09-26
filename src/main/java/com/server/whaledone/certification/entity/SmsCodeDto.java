package com.server.whaledone.certification.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SmsCodeDto{

    private String code;

    private String phoneNumber;

    private long expiredTime;

    public static SmsCodeDto of(String code, String phoneNumber, long expiredTime) {
        return SmsCodeDto.builder()
                .code(code)
                .phoneNumber(phoneNumber)
                .expiredTime(expiredTime)
                .build();
    }

    public String getMinute() {
        return String.valueOf(this.expiredTime / 1000 / 60);
    }

    public String getSecond() {
        return String.valueOf(this.expiredTime / 1000 % 60);
    }
}
