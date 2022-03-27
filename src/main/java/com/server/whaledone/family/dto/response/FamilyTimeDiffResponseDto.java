package com.server.whaledone.family.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FamilyTimeDiffResponseDto {

    @Schema(description = "유저 idx")
    private Long id;

    @Schema(description = "국가 코드")
    private String countryCode;

    @Schema(description = "한국 기준 시차")
    private String timeDiff;

    @Builder
    public FamilyTimeDiffResponseDto(User user) {
        this.id = user.getId();
        this.countryCode = user.getCountry().getCountryCode();
        this.timeDiff = user.getCountry().getTimeDiff();
    }
}
