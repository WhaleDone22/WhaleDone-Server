package com.server.whaledone.posts.dto;

import com.server.whaledone.config.Entity.ContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdatePostsRequestDto {
    @NotBlank
    @Schema(name = "피드 제목")
    private String title;

    @NotBlank
    @Schema(name = "텍스트 내용 또는 URL")
    private String content;

    @Schema(name = "컨텐츠 유형 : 텍스트 = 0, 이미지 = 1, 오디오 = 2")
    private ContentType type;
}
