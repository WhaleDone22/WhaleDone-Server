package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class ResetPasswordRequestDto {

    @Email
    @Schema(description = "비밀번호 재발급 메일을 받을 유저 이메일", example = "test@naver.com")
    private String email;

}
