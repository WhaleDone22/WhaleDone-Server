package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFamilyResponseDto {

    @Schema(description = "초대 코드")
    String invitationCode;

    @Schema(description = "초대 코드 남은 유효 시간", example = "HH")
    String remainingValidTimeHour;

    @Schema(description = "초대 코드 남은 유효 시간", example = "MM")
    String remainingValidTimeMinute;

    @Schema(description = "초대 코드 남은 유효 시간", example = "SS")
    String remainingValidTimeSecond;

    @Schema(description = "생성된 가족 id")
    Long familyId;
}
