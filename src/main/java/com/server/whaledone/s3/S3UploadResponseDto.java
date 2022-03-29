package com.server.whaledone.s3;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class S3UploadResponseDto {

    @Schema(description = "S3 URL")
    private String url;
}
