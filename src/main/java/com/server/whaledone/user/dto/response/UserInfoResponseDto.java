package com.server.whaledone.user.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoResponseDto {

    @Schema(description = "유저 닉네임")
    private String nickName;

    @Schema(description = "국가 코드", example = "KR")
    private String countryCode;

    @Schema(description = "유저 전화 번호", example = "01012345678 (-제외)")
    private String phoneNumber;

    @Schema(description = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Schema(description = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @Schema(description = "가족 채널 이름")
    private String groupName;

    @Schema(description = "가족 idx")
    private Long familyId;

    @Schema(description = "서비스 알람 수신 시각")
    private String alarmTime;

    public UserInfoResponseDto(User user) {
        this.nickName = user.getNickName();
        this.countryCode = user.getCountry().getCountryCode();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImgUrl = user.getProfileImgUrl();
        this.alarmStatus = user.getAlarmStatus();
        this.groupName = user.getFamily().getFamilyName();
        this.familyId = user.getFamily().getId();
        this.alarmTime = user.getAlarmTime();
    }
}
