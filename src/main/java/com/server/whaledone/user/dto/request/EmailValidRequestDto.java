package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EmailValidRequestDto {

    @Email
    @NotBlank
    @Schema(description = "유저 이메일", example = "test@naver.com")
    private String email;
}
