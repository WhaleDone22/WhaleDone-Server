package com.server.whaledone.reaction;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.MultipleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.reaction.dto.request.ChangeReactionRequestDto;
import com.server.whaledone.reaction.dto.request.SaveReactionRequestDto;
import com.server.whaledone.reaction.dto.response.GetReactionsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Reaction API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;
    private final ResponseService responseService;

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "리액션 등록 API", description = "토큰값과 함께 게시글에 리액션을 등록한다.")
    @PostMapping("/posts/{postId}/reaction")
    public CommonResult saveReaction(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable Long postId,
                                  @RequestBody @Valid SaveReactionRequestDto dto) {
        reactionService.saveReaction(userDetails, postId, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "리액션 조회 API", description = "토큰값과 함께 게시글의 리액션을 조회한다.")
    @GetMapping("/posts/{postId}/reactions")
    public MultipleResult<GetReactionsResponseDto> getReactions(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @PathVariable Long postId) {
        return responseService.getMultipleResult(reactionService.getReactions(userDetails, postId));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "리액션 수정 API", description = "토큰값과 함께 게시글의 리액션을 수정한다.")
    @PatchMapping("/posts/{postId}/reactions/{reactionId}")
    public CommonResult changeReaction(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable Long postId,
                                       @PathVariable Long reactionId,
                                       @RequestBody ChangeReactionRequestDto dto) {
        reactionService.changeReaction(userDetails, postId, reactionId, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "리액션 삭제 API", description = "토큰값과 함께 게시글의 리액션을 삭제한다.")
    @PatchMapping("/posts/{postId}/reactions/{reactionId}/status")
    public CommonResult deleteReaction(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable Long postId,
                                       @PathVariable Long reactionId) {
        reactionService.deleteReaction(userDetails, postId, reactionId);
        return responseService.getSuccessResult();
    }
}
