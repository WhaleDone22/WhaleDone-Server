package com.server.whaledone.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class SignInRequestDto {

    @Email
    @NotBlank
    @ApiModelProperty(example = "유저 이메일")
    private String email;

    @NotBlank
    @Min(value = 8)
    @ApiModelProperty(example = "유저 비밀번호")
    private String password;
}
