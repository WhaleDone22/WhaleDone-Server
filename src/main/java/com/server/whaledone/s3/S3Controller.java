package com.server.whaledone.s3;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.s3.S3FileManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "S3 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class S3Controller {

    private final S3FileManager s3FileManager;
    private final ResponseService responseService;

    @Operation(summary = "S3 Upload API", description = "이미지, 오디오 파일을 MultipartFile로 전송하고 url을 리턴한다.")
    @PostMapping("/content")
    public SingleResult<S3UploadResponseDto> uploadContent(@RequestParam MultipartFile multipartFile) throws IOException {
        return responseService.getSingleResult(S3UploadResponseDto.builder()
                .url(s3FileManager.upload(multipartFile))
                .build());
    }
}
