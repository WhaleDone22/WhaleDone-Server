package com.server.whaledone.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateProfileImgRequestDto {

    @NotBlank
    @Schema(description = "이미지 저장 후 리턴받은 URL")
    private String profileImgUrl;
}
