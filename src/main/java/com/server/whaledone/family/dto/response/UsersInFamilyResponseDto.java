package com.server.whaledone.family.dto.response;

import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsersInFamilyResponseDto {

    @Schema(example = "유저 닉네임")
    private String nickName;

    @Schema(example = "국가 코드")
    private String nation;

    @Schema(example = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Builder
    public UsersInFamilyResponseDto(User user) {
        this.nickName = user.getNickName();
        this.nation = user.getNation();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
