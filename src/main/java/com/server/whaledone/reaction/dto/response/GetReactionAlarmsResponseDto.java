package com.server.whaledone.reaction.dto.response;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.reaction.entity.Reaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
public class GetReactionAlarmsResponseDto {

    @Schema(description = "리액션 남긴 사람 id")
    private Long authorId;

    @Schema(description = "리액션 남긴 사람 닉네임")
    private String nickName;

    @Schema(description = "게시글 id")
    private Long postId;

    @Schema(description = "리액션 id")
    private Long reactionId;

    @Schema(description = "리액션 type", example = "TEXT, IMAGE, AUDIO")
    private ContentType reactionType;

    @Schema(description = "리액션 내용", example = "TEXT 또는 S3 URL")
    private String reactionContent;

    @Schema(description = "리액션 생성 날짜")
    LocalDate createdDate;

    @Schema(description = "요일")
    DayOfWeek displayName;

    @Builder
    public GetReactionAlarmsResponseDto(Reaction reaction) {
        this.authorId = reaction.getAuthor().getId();
        this.nickName = reaction.getAuthor().getNickName();
        this.postId = reaction.getPost().getId();
        this.reactionId = reaction.getId();
        this.reactionType = reaction.getType();
        this.reactionContent = reaction.getContent();
        this.createdDate = reaction.getCreatedAt().toLocalDate();
        this.displayName = reaction.getCreatedAt().getDayOfWeek();
    }
}
