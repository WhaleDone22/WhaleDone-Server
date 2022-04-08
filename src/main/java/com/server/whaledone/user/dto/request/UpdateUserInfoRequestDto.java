package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@ToString
public class UpdateUserInfoRequestDto {

    @NotBlank
    @Schema(description = "국가 코드", example = " ex) KR")
    private String countryCode;

    @NotBlank
    @Schema(description = "유저 전화 번호", example = "01012345678 (-제외)")
    private String phoneNumber;

    @NotBlank
    @Length(max = 12)
    @Schema(description = "가족 채널 명")
    private String familyName;

    @Schema(description = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

//    @Schema(description = "서비스 알람 수신 시각")
//    private String alarmTime;

    @NotBlank
    @Schema(description = "이미지 저장 후 리턴받은 URL")
    private String profileImgUrl;
}
