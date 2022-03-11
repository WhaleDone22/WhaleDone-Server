package com.server.whaledone.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ValidateInvitationCodeRequestDto {

    @NotBlank
    @Schema(name = "초대 코드")
    private String invitationCode;
}
