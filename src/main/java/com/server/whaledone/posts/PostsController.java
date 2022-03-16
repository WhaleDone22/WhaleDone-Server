package com.server.whaledone.posts;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.posts.dto.SavePostsRequestDto;
import com.server.whaledone.posts.dto.UpdatePostsRequestDto;
import com.server.whaledone.posts.dto.response.PostsMapToDateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Post API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final ResponseService responseService;

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "일상 공유 API", description = "토큰값과 함께 게시글을 공유한다.")
    @PostMapping("/users/auth/post")
    public CommonResult savePosts(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @RequestBody @Valid SavePostsRequestDto dto) {
        postsService.savePosts(userDetails, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "전체 일상 조회 API", description = "가족 전체의 게시글을 조회한다.")
    @GetMapping("/users/auth/family-posts")
    public SingleResult<PostsMapToDateResponseDto> getFamilyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(postsService.getFamilyPosts(userDetails));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "내 일상 조회 API", description = "나의 일상 게시글을 조회한다.")
    @GetMapping("/users/auth/posts")
    public SingleResult<PostsMapToDateResponseDto> getMyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(postsService.getMyPosts(userDetails));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "내 게시글 삭제 API", description = "해당 게시글이 요청자 소유인지 검증 후, 삭제 상태로 변경한다.")
    @PatchMapping("/users/auth/posts/{postId}/status")
    public CommonResult deletePosts(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
        postsService.deletePosts(userDetails, postId);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "내 게시글 수정 API", description = "해당 게시글이 요청자 소유인지 검증 후, 게시글 내용을 수정한다.")
    @PatchMapping("/users/auth/posts/{postId}")
    public CommonResult updatePosts(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable Long postId,
                                    @RequestBody UpdatePostsRequestDto dto) {
        postsService.updatePosts(userDetails, postId, dto);
        return responseService.getSuccessResult();
    }
}
