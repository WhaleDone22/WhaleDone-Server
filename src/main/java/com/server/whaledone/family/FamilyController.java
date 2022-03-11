package com.server.whaledone.family;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.dto.response.CreateFamilyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Family API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final ResponseService responseService;

    @Operation(summary = "새로운 가족 생성 API",
            description = "회원가입 후 새로운 초대 코드 만들기를 누르면 새로운 가족이 생성되고 token 기반으로 생성자가 참여된다.")
    @PostMapping("/family")
    public SingleResult<CreateFamilyResponseDto> createFamily(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(familyService.createFamily(userDetails));
    }
}
