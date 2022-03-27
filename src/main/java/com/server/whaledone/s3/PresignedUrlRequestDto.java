package com.server.whaledone.s3;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PresignedUrlRequestDto {

    @NotBlank
    @Schema(description = "파일 이름")
    private String fileName;
}
