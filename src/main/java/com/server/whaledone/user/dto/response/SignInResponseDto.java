package com.server.whaledone.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    @Schema(example = "유저 db id")
    private Long userId;

    @Schema(example = "유저 이메일")
    private String email;

    @Schema(example = "유저 닉네임")
    private String nickName;

    @Schema(example = "유저 access token")
    private String jwtToken;
}
