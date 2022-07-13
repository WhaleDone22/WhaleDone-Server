package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FamilyInfoResponseDto {

    @Schema(description = "가족 채널 이름")
    private String familyName;

    @Builder
    public FamilyInfoResponseDto(String familyName) {
        this.familyName = familyName;
    }
}
