package com.server.whaledone.posts.dto;

import com.server.whaledone.config.Entity.ContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionCountDto {

    @Schema(description = "리액션 type", example = "TEXT, IMAGE, AUDIO")
    private ContentType type;

    @Schema(description = "해당 리액션 개수")
    private Long count;

    @Builder
    public ReactionCountDto(ContentType type, Long count) {
        this.type = type;
        this.count = count;
    }
}
