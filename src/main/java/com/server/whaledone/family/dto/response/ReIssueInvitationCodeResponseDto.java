package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReIssueInvitationCodeResponseDto {

    @Schema(description = "초대 코드")
    String invitationCode;
    
    @Schema(description = "남은 시간")
    String hour;

    @Schema(description = "남은 분", example = "mm")
    String minute;

    @Schema(description = "남은 초", example = "ss")
    String second;

    @Builder
    ReIssueInvitationCodeResponseDto(String invitationCode, String hour, String minute, String second) {
        this.invitationCode = invitationCode;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
