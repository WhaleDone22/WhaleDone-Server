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
    @Schema(description = "유저 이메일")
    private String email;

    @NotBlank
    @Length(max = 5)
    @Pattern(regexp = "^[0-9a-zA-Z가-힣*,._+!?]*$")
    @Schema(description = "유저 닉네임")
    private String nickName;

    @Schema(description = "국가 코드", example = "KR")
    private String countryCode;

    @NotBlank
    @Length(min = 8)
    @Pattern(regexp = "^[0-9a-zA-Z]*$")
    @Schema(description = "유저 비밀번호")
    private String password;

    @Schema(description = "유저 전화번호", example = "01012345678 (-제외)")
    private String phoneNumber;

    @Schema(description = "서비스 알람 수신 여부")
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
