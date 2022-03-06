package com.server.whaledone.user;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.user.dto.request.EmailValidRequestDto;
import com.server.whaledone.user.dto.request.NicknameValidRequestDto;
import com.server.whaledone.user.dto.request.SignInRequestDto;
import com.server.whaledone.user.dto.request.SignUpRequestDto;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import com.server.whaledone.user.dto.response.UserInfoResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"User API"})
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @Operation(summary = "회원가입 API", description = "회원가입에 필요한 json 데이터를 받아서 저장 후 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/sign-up")
    public SingleResult<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return responseService.getSingleResult(userService.signUp(dto));
    }

    @Operation(summary = "로그인 API", description = "로그인에 필요한 json 데이터(email, password)를 받아서 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/sign-in")
    public SingleResult<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto dto) {
        return responseService.getSingleResult(userService.signIn(dto));
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    @Operation(summary = "회원 조회 API", description = "전달 받은 token을 이용해서 유저 정보 dto를 리턴한다.")
    @GetMapping("/user")
    public SingleResult<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(userService.getUserInfo(userDetails));
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    @Operation(summary = "회원 탈퇴 API", description = "토큰을 기준으로 회원의 계정 status를 변경한다.")
    @PatchMapping("/user/status")
    public CommonResult deleteUserAccount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUserAccount(userDetails);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "이메일 검증 API", description = "전달 받은 이메일을 이용해서 검증 결과를 리턴한다.")
    @PostMapping("/user/validation/email")
    public CommonResult getEmailValidationStatus(@RequestBody @Valid EmailValidRequestDto email) {
        userService.getEmailValidationStatus(email);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "닉네임 검증 API", description = "전달 받은 닉네임을 이용해서 검증 결과를 리턴한다.")
    @PostMapping("/user/validation/nickname")
    public CommonResult getNicknameValidationStatus(@RequestBody @Valid NicknameValidRequestDto nickName) {
        userService.getNicknameValidationStatus(nickName);
        return responseService.getSuccessResult();
    }
}
