package com.server.whaledone.user;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.user.dto.request.SignInRequestDto;
import com.server.whaledone.user.dto.request.SignUpRequestDto;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public SingleResult<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto, Errors errors) {
//        if (errors.hasErrors()) {}
        return responseService.getSingleResult(userService.signUp(dto));
    }

    @Operation(summary = "로그인 API", description = "로그인에 필요한 json 데이터(email, password)를 받아서 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/sign-in")
    public SingleResult<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto dto, Errors errors) {
//        if(errors.hasErrors())
        return responseService.getSingleResult(userService.signIn(dto));
    }
}
