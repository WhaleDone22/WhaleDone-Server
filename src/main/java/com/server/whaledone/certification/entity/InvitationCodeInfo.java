package com.server.whaledone.certification.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class InvitationCodeInfo extends CustomCodeInfo {

    @Schema(name = "가족 id")
    private Long familyId;

    @Builder
    InvitationCodeInfo(Date expiredAt, Long familyId) {
        super(expiredAt);
        this.familyId = familyId;
    }
}
