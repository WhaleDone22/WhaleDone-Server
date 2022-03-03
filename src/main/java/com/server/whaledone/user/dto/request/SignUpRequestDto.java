package com.server.whaledone.user.dto.request;

import com.server.whaledone.user.entity.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@Builder
public class SignUpRequestDto {
    @Email
    @NotNull
    private String email;

    @NotBlank
    @Length(min = 5)
    // @Pattern(regexp = )
    private String nickName;

    // 추후에 국가 클래스로 변경
    private String nation;

    @NotBlank
    @Length(min = 8)
    private String password;

    private String phoneNumber;

    private String profileImgUrl;

    // 서비스 알람 수신 여부
    private Boolean alarmStatus;

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
