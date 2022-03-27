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
    @Schema(description = "리액션 내용 또는 URL")
    private String content;

    @Schema(description = "컨텐츠 유형", example = "TEXT, IMAGE, AUDIO")
    private ContentType type;
}
