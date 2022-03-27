package com.server.whaledone.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateFamilyNameRequestDto {

    @NotBlank
    @Length(max = 10)
    @Schema(description = "변경될 가족 채널 이름")
    private String updateName;
}
