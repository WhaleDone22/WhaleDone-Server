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

    @Schema(name = "요청 결과 ID")
    private String requestId;

    @Schema(name = "요청 시간")
    private LocalDateTime requestTime;

    @Schema(name = "상태 코드")
    private String statusCode;

    @Schema(name = "상태명")
    private String statusName;

    @Schema(name = "남은 유효시간 (mm)")
    private String minute;

    @Schema(name = "남은 유효시간 (ss)")
    private String second;
}
