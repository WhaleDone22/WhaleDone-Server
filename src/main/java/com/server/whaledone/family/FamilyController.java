package com.server.whaledone.family;

import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.MultipleResult;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.dto.request.UpdateFamilyNameRequestDto;
import com.server.whaledone.family.dto.request.ValidateInvitationCodeRequestDto;
import com.server.whaledone.family.dto.response.CreateFamilyResponseDto;
import com.server.whaledone.family.dto.response.ReIssueInvitationCodeResponseDto;
import com.server.whaledone.family.dto.response.UsersInFamilyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Family API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final ResponseService responseService;

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "새로운 가족 채널 생성 API",
            description = "회원가입 후 새로운 초대 코드 만들기를 누르면 새로운 가족이 생성되고 token 기반으로 생성자가 참여된다.")
    @PostMapping("/family")
    public SingleResult<CreateFamilyResponseDto> createFamily(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return responseService.getSingleResult(familyService.createFamily(userDetails));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "가족 채널 이름 변경 API",
            description = "가족 채널명을 수정한다.")
    @PatchMapping("/families/{familyId}/name")
    public CommonResult updateFamilyName(@PathVariable Long familyId, @RequestBody @Valid UpdateFamilyNameRequestDto dto) {
        familyService.updateFamilyName(familyId, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "가족 채널 초대 코드 검증 API", description = "초대 코드를 입력받아서 해당 가족 그룹에 유저를 추가한다.")
    @PatchMapping("/family/validation/invitationCode")
    public CommonResult validateInvitationCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody ValidateInvitationCodeRequestDto dto) {
        familyService.validateInvitationCode(userDetails, dto);
        return responseService.getSuccessResult();
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "해당 가족 채널에 있는 가족 구성원 조회 API",
            description = "갖고 있는 familyId를 통해 해당 가족 구성원을 조회한다.")
    @GetMapping("/families/{familyId}/users")
    public MultipleResult<UsersInFamilyResponseDto> getUsersInFamily(@PathVariable Long familyId) {
        return responseService.getMultipleResult(familyService.getUsersInFamily(familyId));
    }

    @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 access_token", required = true)
    @Operation(summary = "가족 초대 코드 재발급 API",
            description = "다른 구성원을 초대하기 위해 기존 구성원이 초대 코드를 재발급받는다.")
    @PostMapping("/families/{familyId}/new-code")
    public SingleResult<ReIssueInvitationCodeResponseDto> reIssueInvitationCode(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long familyId) {
        return responseService.getSingleResult(familyService.reIssueInvitationCode(userDetails, familyId));
    }
}
