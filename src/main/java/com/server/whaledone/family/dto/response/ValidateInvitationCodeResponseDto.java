package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class ValidateInvitationCodeResponseDto {

    @Schema(description = "가족 idx")
    private Long familyId;
}
