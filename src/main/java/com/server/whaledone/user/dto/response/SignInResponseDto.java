package com.server.whaledone.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    private Long userId;

    private String email;

    private String nickName;

    private String jwtToken;
}
