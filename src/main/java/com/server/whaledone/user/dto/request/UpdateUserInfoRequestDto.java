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
    // 추후에 국가 클래스로 변경
    @NotBlank
    @Schema(example = "국가 코드")
    private String nation;

    @NotBlank
    @Schema(example = "유저 전화 번호")
    private String phoneNumber;

    @NotBlank
    @Length(max = 10)
    @Schema(example = "가족 채널 명")
    private String familyName;

    @NotBlank
    @Schema(example = "서비스 알람 수신 여부")
    private Boolean alarmStatus;

    @Schema(example = "서비스 알람 수신 시각")
    private String alarmTime;
}
