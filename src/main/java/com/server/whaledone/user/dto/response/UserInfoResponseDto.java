package com.server.whaledone.user.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoResponseDto {

    @ApiModelProperty(example = "유저 닉네임")
    private String nickName;

    // 추후에 국가 클래스로 변경
    @ApiModelProperty(example = "국가 코드")
    private String nation;

    @ApiModelProperty(example = "유저 전화 번호")
    private String phoneNumber;

    @ApiModelProperty(example = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @ApiModelProperty(example = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @ApiModelProperty(example = "가족 채널 이름")
    private String groupName;

    @ApiModelProperty(example = "서비스 알람 수신 시각")
    private String alarmTime;

    public UserInfoResponseDto(User user) {
        this.nickName = user.getNickName();
        this.nation = user.getNation();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImgUrl = user.getProfileImgUrl();
        this.alarmStatus = user.getAlarmStatus();
        this.groupName = user.getGroupName();
        this.alarmTime = user.getAlarmTime();
    }
}
