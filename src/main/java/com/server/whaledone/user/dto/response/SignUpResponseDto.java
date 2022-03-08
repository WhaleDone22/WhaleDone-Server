package com.server.whaledone.user.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpResponseDto {

    @Schema(example = "유저 db id")
    private Long userId;

    @Schema(example = "유저 이메일")
    private String email;

    @Schema(example = "유저 닉네임")
    private String nickName;

    // 추후에 국가 클래스로 변경
    @Schema(example = "국가 코드")
    private String nation;

    @Schema(example = "유저 전화 번호")
    private String phoneNumber;

    @Schema(example = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Schema(example = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @Schema(example = "유저 access token")
    private String jwtToken;

    public SignUpResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.nation = user.getNation();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImgUrl = user.getProfileImgUrl();
        this.alarmStatus = user.getAlarmStatus();
    }
}
