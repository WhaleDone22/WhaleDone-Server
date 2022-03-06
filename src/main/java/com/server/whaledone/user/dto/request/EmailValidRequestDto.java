package com.server.whaledone.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EmailValidRequestDto {

    @Email
    @NotBlank
    @ApiModelProperty(example = "유저 이메일")
    private String email;
}
