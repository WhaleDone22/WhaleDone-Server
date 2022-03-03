package com.server.whaledone.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    private Long accountId;

    private String email;

    private String nickName;

    private String jwtToken;
}
