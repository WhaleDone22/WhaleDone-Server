package com.server.whaledone.sms.dto.standard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponseDto {

    @Schema(description = "요청 결과 ID", example = "1")
    private String requestId;

    @Schema(description = "요청 시간")
    private LocalDateTime requestTime;

    @Schema(description = "상태 코드")
    private String statusCode;

    @Schema(description = "상태명")
    private String statusName;

    @Schema(description = "남은 유효시간", example = "mm")
    private String minute;

    @Schema(description = "남은 유효시간", example = "ss")
    private String second;
}
