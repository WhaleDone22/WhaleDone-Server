package com.server.whaledone.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordResponseDto {

    @Schema(description = "임시 비밀번호")
    private String tempPassword;
}
