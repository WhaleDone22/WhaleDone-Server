package com.server.whaledone.user;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.user.dto.request.*;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import com.server.whaledone.user.dto.response.UserInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @Operation(summary = "회원가입 API", description = "회원가입에 필요한 json 데이터를 받아서 저장 후 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/user/sign-up")
    public SingleResult<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return responseService.getSingleResult(userService.signUp(dto));
    }

    @Operation(summary = "로그인 API", description = "로그인에 필요한 json 데이터(email, password)를 받아서 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/user/sign-in")
    public SingleResult<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto dto) {
        return responseService.getSingleResult(userService.signIn(dto));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "회원 조회 API", description = "전달 받은 token을 이용해서 유저 정보 dto를 리턴한다.")
    @GetMapping("/users/auth")
    public SingleResult<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(userService.getUserInfo(userDetails));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "회원 탈퇴 API", description = "토큰을 기준으로 회원의 계정 status를 변경한다.")
    @PatchMapping("/users/auth/status")
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

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "프로필 사진 변경 API", description = "presignedURL을 이용해서 저장한 컨텐츠의 접근 URL을 토큰과 함께 전달한다.")
    @PatchMapping("/users/auth/profile-image")
    public CommonResult updateUserProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody UpdateProfileImgRequestDto dto) {
        userService.updateProfileImg(userDetails, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "마이페이지 수정 API", description = "국가, 전화번호, 가족 채널명, 알람 여부, 알람 시간을 받아서 업데이트한다." +
            "채널 명만 바뀌어도 나머지 필드도 기존값과 함께 받는다.")
    @PatchMapping("/users/auth/information")
    public CommonResult updateUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid UpdateUserInfoRequestDto dto) {
        userService.updateUserInfo(userDetails, dto);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "비밀번호 재발급 메일 API", description = "해당 API를 이용해서 메일로 임시 비밀번호를 유저에게 전송한다.")
    @PostMapping("/user/new-password")
    public CommonResult sendReIssuePasswordMail(@RequestBody @Valid ReissuePasswordRequestDto dto) {
        userService.reIssuePassword(dto);
        return responseService.getSuccessResult();
    }
}
