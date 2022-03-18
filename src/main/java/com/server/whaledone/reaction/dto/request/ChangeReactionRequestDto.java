package com.server.whaledone.reaction.dto.request;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.reaction.entity.Reaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ChangeReactionRequestDto {

    @NotBlank
    @Schema(name = "리액션 내용 또는 URL")
    private String content;

    @Schema(name = "컨텐츠 유형 : 텍스트 = 0, 이미지 = 1, 오디오 = 2")
    private ContentType type;
}
