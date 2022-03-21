package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFamilyResponseDto {

    @Schema(example = "초대 코드")
    String invitationCode;

    @Schema(example = "초대 코드 남은 유효 시간 (HH)")
    String remainingValidTimeHour;

    @Schema(example = "초대 코드 남은 유효 시간 (MM)")
    String remainingValidTimeMinute;

    @Schema(example = "초대 코드 남은 유효 시간 (SS)")
    String remainingValidTimeSecond;
}
