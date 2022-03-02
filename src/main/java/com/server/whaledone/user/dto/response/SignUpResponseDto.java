package com.server.whaledone.user.dto.response;

import com.server.whaledone.user.entity.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SignUpResponseDto {

    private Long accountId;

    @Email
    @NotNull
    private String email;

    @NotBlank
    @Length(min = 5)
    // @Pattern(regexp = )
    private String nickName;

    // 추후에 국가 클래스로 변경
    private String nation;

    private String phoneNumber;

    private String profileImgUrl;

    // 서비스 알람 수신 여부
    private Boolean alarmStatus;

//    private String jwtToken;

    public SignUpResponseDto(User user) {
        this.accountId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.nation = user.getNation();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImgUrl = user.getProfileImgUrl();
        this.alarmStatus = user.getAlarmStatus();
    }

}
