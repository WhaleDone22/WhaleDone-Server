package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class NicknameValidRequestDto {
    @NotBlank
    @Length(max = 5)
    @Pattern(regexp = "^[0-9a-zA-Z가-힣*,._+!?]*$")
    @Schema(description = "유저 닉네임")
    private String nickName;
}
