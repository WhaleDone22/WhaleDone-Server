package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignInRequestDto {

    @Email
    @NotBlank
    @Schema(description = "유저 이메일")
    private String email;

    @NotBlank
    @Length(min = 8)
    @Schema(description = "유저 비밀번호")
    private String password;
}
