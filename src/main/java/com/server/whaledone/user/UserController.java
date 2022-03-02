package com.server.whaledone.user;

import com.server.whaledone.user.dto.request.SignUpRequestDto;
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

    @Operation(summary = "회원가입 API", description = "회원가입에 필요한 json 데이터를 받아서 저장 후 토큰을 포함한 dto를 리턴한다.")
    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequestDto dto, Errors errors) {
//        if (errors.hasErrors()) {}
        return userService.signUp(dto);
    }

    @PostMapping("/sign-in")
    public String temp() {
        return "완료";
    }
}
