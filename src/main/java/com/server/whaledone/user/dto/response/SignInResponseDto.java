package com.server.whaledone.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    @Schema(description = "유저 id")
    private Long userId;

    @Schema(description = "유저 이메일")
    private String email;

    @Schema(description = "유저 닉네임")
    private String nickName;

    @Schema(description = "유저 access token")
    private String jwtToken;

    @Schema(description = "가족 idx", example = "가족이 없으면 -1")
    private Long familyId;

    @Schema(description = "가족 유무", example = "true/false")
    private boolean hasFamily;
}
