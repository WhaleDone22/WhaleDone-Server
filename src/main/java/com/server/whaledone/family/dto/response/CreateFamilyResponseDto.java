package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFamilyResponseDto {

    @Schema(example = "초대 코드")
    String invitationCode;

    @Schema(example = "초대 코드 유효 시간(ms 단위) (HH:MM:SS)")
    String remainingValidTime;
}
