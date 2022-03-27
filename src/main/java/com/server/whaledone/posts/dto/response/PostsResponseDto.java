package com.server.whaledone.posts.dto.response;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.posts.dto.ReactionCountDto;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.entity.Reaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostsResponseDto {

    @Schema(description = "게시글 idx")
    Long id;

    @Schema(description = "게시글 생성 날짜")
    LocalDate createdDate;

    @Schema(description = "요일")
    DayOfWeek displayName;

    @Schema(description = "글 작성자")
    String authorName;

    @Schema(description = "작성자 idx")
    Long authorIdx;

    @Schema(description = "작성자 썸네일 URL")
    String profileImgUrl;

    @Schema(description = "게시글 제목")
    String title;

    @Schema(description = "게시글 내용")
    String contents;

    @Schema(description = "게시글 type")
    ContentType type;

    @Schema(description = "리액션 개수 리스트")
    List<ReactionCountDto> reactionCount = new ArrayList<>();

    // 리액션 리스트
    @Builder
    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.createdDate = posts.getCreatedAt().toLocalDate();
        this.displayName = this.createdDate.getDayOfWeek();
        this.authorName = posts.getAuthor().getNickName();
        this.authorIdx = posts.getAuthor().getId();
        this.profileImgUrl = posts.getAuthor().getProfileImgUrl();
        this.title = posts.getTitle();
        this.contents = posts.getContent();
        this.type = posts.getType();
    }

    public void setReactionCounts(List<ReactionCountDto> countDtoList) {
        this.reactionCount = countDtoList;
    }
}
