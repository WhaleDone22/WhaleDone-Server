package com.server.whaledone.certification.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Getter
public abstract class CustomCodeInfo {

    @Schema(description = "인증 만료 날짜")
    private Date expiredTime;

    CustomCodeInfo(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
