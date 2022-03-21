package com.server.whaledone.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReIssueInvitationCodeResponseDto {

    @Schema(name = "초대 코드")
    String invitationCode;
    
    @Schema(name = "남은 시간")
    String hour;

    @Schema(name = "남은 분")
    String minute;

    @Schema(name = "남은 초")
    String second;

    @Builder
    ReIssueInvitationCodeResponseDto(String invitationCode, String hour, String minute, String second) {
        this.invitationCode = invitationCode;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
