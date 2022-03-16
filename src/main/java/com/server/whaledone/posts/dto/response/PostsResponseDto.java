package com.server.whaledone.posts.dto.response;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.posts.entity.Posts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
public class PostsResponseDto {

    @Schema(name = "게시글 idx")
    Long id;

    @Schema(name = "게시글 생성 날짜")
    LocalDate createdDate;

    @Schema(name = "요일")
    DayOfWeek displayName;

    @Schema(name = "글 작성자")
    String authorName;

    @Schema(name = "작성자 idx")
    Long authorIdx;

    @Schema(name = "작성자 썸네일 URL")
    String profileImgUrl;

    @Schema(name = "게시글 제목")
    String title;

    @Schema(name = "게시글 내용")
    String contents;

    @Schema(name = "게시글 type")
    ContentType type;

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
}
