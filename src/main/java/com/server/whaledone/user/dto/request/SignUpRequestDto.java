package com.server.whaledone.user.dto.request;

import com.server.whaledone.country.entity.Country;
import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class SignUpRequestDto {
    @Email
    @NotNull
    @Schema(example = "유저 이메일")
    private String email;

    @NotBlank
    @Length(max = 5)
    @Pattern(regexp = "^[0-9a-zA-Z가-힣*,._+!?]*$")
    @Schema(example = "유저 닉네임")
    private String nickName;

    @Schema(example = "국가 코드")
    private String countryCode;

    @NotBlank
    @Length(min = 8)
    @Pattern(regexp = "^[0-9a-zA-Z]*$")
    @Schema(example = "유저 비밀번호")
    private String password;

    @Schema(example = "유저 전호번호 ex) ")
    private String phoneNumber;

    @Schema(example = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Schema(example = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    public User toEntity(Country country) {
        return User.builder()
                .country(country)
                .phoneNumber(this.phoneNumber)
                .alarmStatus(this.alarmStatus)
                .email(this.email)
                .password(this.password)
                .nickName(this.nickName)
                .build();
    }
}
