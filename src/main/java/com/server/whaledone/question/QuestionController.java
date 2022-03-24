package com.server.whaledone.question;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.MultipleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.question.dto.QuestionResponseDto;
import com.server.whaledone.reaction.dto.request.SaveReactionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Question API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final ResponseService responseService;

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "질문 조회 API", description = "카테고리별로 랜덤한 질문을 리턴한다.")
    @GetMapping("/questions")
    public MultipleResult<QuestionResponseDto> getQuestionPerCategory(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getMultipleResult(questionService.getQuestionsPerCategory());
    }
}
