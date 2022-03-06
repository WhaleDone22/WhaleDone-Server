package com.server.whaledone.user.dto.request;

import com.server.whaledone.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@Builder
public class SignUpRequestDto {
    @Email
    @NotNull
    @ApiModelProperty(example = "유저 이메일")
    private String email;

    @NotBlank
    @Length(min = 5)
    // @Pattern(regexp = )
    @ApiModelProperty(example = "유저 닉네임")
    private String nickName;

    // 추후에 국가 클래스로 변경
    @ApiModelProperty(example = "국가 코드")
    private String nation;

    @NotBlank
    @Length(min = 8)
    @ApiModelProperty(example = "유저 비밀번호")
    private String password;

    @ApiModelProperty(example = "유저 전호번호 ex) ")
    private String phoneNumber;

    @ApiModelProperty(example = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @ApiModelProperty(example = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @ApiModelProperty(example = "유저 access token")
    private String jwtToken;

    public User toEntity() {
        return User.builder()
                .nation(this.nation)
                .phoneNumber(this.phoneNumber)
                .alarmStatus(this.alarmStatus)
                .email(this.email)
                .password(this.password)
                .nickName(this.nickName)
                .build();
    }
}
