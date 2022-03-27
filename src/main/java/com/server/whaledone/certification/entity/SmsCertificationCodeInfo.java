package com.server.whaledone.certification.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class SmsCertificationCodeInfo extends CustomCodeInfo {

    @Schema(description = "전화번호")
    private String phoneNumber;

    @Builder
    SmsCertificationCodeInfo(Date expiredTime, String phoneNumber) {
        super(expiredTime);
        this.phoneNumber = phoneNumber;
    }
}
