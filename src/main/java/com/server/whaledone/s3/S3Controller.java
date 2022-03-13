package com.server.whaledone.s3;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.s3.S3FileManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "S3 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class S3Controller {

    private final S3FileManager s3FileManager;
    private final ResponseService responseService;

    @Operation(summary = "presignedURL API", description = "presignedURL을 리턴 받기 위해 파일 이름을 전달한다.")
    @PostMapping("/content/presigned-url")
    public SingleResult<PresignedUrlResponseDto> createPresignedUrl(@RequestBody @Valid PresignedUrlRequestDto dto) {
        return responseService.getSingleResult(PresignedUrlResponseDto.builder()
                .presignedUrl(s3FileManager.getPresignedUrl(dto.getFileName()))
                .build());
    }
}
