package com.server.whaledone.certification.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CustomCodeDto {

    private String code;

    private CustomCodeInfo info;

    public String getHour() {
        long minute = getRemainTime() / 60;
        return Long.toString(minute / 60);
    }

    public String getMinute() {
        long minute = getRemainTime() / 60;
        return Long.toString(minute % 60);
    }

    public String getSecond() {
        return Long.toString(getRemainTime() % 60);
    }

    private long getRemainTime() {
        return (this.getInfo().getExpiredTime().getTime() - new Date().getTime())/1000;
    }
}
