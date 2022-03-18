package com.server.whaledone.reaction.dto.response;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.reaction.entity.Reaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetReactionsResponseDto {

    @Schema(name = "리액션 idx")
    Long id;

    @Schema(name = "리액션 생성 날짜")
    LocalDateTime createdDate;

    @Schema(name = "리액션 작성자")
    String authorName;

    @Schema(name = "작성자 idx")
    Long authorIdx;

    @Schema(name = "작성자 썸네일 URL")
    String profileImgUrl;

    @Schema(name = "리액션 내용 : text or URL")
    String contents;

    @Schema(name = "리액션 type")
    ContentType type;

    @Builder
    public GetReactionsResponseDto(Reaction reaction) {
        this.id = reaction.getId();
        this.createdDate = reaction.getCreatedAt();
        this.authorName = reaction.getAuthor().getNickName();
        this.authorIdx = reaction.getAuthor().getId();
        this.profileImgUrl = reaction.getAuthor().getProfileImgUrl();
        this.contents = reaction.getContent();
        this.type = reaction.getType();
    }
}
