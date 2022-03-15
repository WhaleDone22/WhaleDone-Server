package com.server.whaledone.posts;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.posts.dto.SavePostsRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
