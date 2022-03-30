package com.server.whaledone.user.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpResponseDto {

    @Schema(description = "유저 db id")
    private Long userId;

    @Schema(description = "유저 이메일")
    private String email;

    @Schema(description = "유저 닉네임")
    private String nickName;

    @Schema(description = "국가 코드")
    private String countryCode;

    @Schema(description = "유저 전화 번호")
    private String phoneNumber;

    @Schema(description = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Schema(description = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @Schema(description = "유저 access token")
    private String jwtToken;

    public SignUpResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.countryCode = user.getCountry().getCountryCode();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImgUrl = user.getProfileImgUrl();
        this.alarmStatus = user.getAlarmStatus();
    }
}
