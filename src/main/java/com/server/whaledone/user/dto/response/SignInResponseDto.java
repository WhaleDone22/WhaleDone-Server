package com.server.whaledone.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    @ApiModelProperty(example = "유저 db id")
    private Long userId;

    @ApiModelProperty(example = "유저 이메일")
    private String email;

    @ApiModelProperty(example = "유저 닉네임")
    private String nickName;

    @ApiModelProperty(example = "유저 access token")
    private String jwtToken;
}
