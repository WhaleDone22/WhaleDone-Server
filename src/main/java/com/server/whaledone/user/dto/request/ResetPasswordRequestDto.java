package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class ResetPasswordRequestDto {

    @NotBlank
    @Length(min = 8)
    @Pattern(regexp = "^[0-9a-zA-Z]*$")
    @Schema(description = "변경할 비밀번호")
    private String password;
}
