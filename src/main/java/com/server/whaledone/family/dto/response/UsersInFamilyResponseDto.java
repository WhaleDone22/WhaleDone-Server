package com.server.whaledone.family.dto.response;

import com.server.whaledone.country.entity.Country;
import com.server.whaledone.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsersInFamilyResponseDto {

    @Schema(description = "유저 idx")
    private Long id;

    @Schema(description = "유저 닉네임")
    private String nickName;

    @Schema(description = "국가 코드")
    private String countryCode;

    @Schema(description = "국가 이름")
    private String countryName;

    @Schema(description = "위도")
    private Double latitude;

    @Schema(description = "경도")
    private Double longitude;

    @Schema(description = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    @Schema(description = "나와 소통횟수 (나 자신은 0)")
    private Long communicationCount;

    @Builder
    public UsersInFamilyResponseDto(User user) {
        Country country = user.getCountry();
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.countryName = country.getCountryName();
        this.countryCode = country.getCountryCode();
        this.latitude = country.getLatitude();
        this.longitude = country.getLongitude();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
