package com.server.whaledone.question.dto;

import com.server.whaledone.question.entity.Question;
import com.server.whaledone.question.entity.QuestionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {

    @Schema(description = "질문 카테고리")
    private QuestionCategory category;

    @Schema(description = "질문 내용")
    private String content;

    @Builder
    public QuestionResponseDto(Question question) {
        this.category = question.getCategory();
        this.content = question.getContent();
    }
}
