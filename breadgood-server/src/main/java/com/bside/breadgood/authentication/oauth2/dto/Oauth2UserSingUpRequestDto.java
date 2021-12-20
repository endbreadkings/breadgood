package com.bside.breadgood.authentication.oauth2.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Oauth2UserSingUpRequestDto {

    // 동의할 약관 정보
    private final List<Long> termsTypeIds;
    // 별명
    private final String nickName;
    // 빵스타일
    private final Long breadStyleId;


    @Builder
    public Oauth2UserSingUpRequestDto(List<Long> termsTypeIds, String nickName, Long breadStyleId) {
        this.termsTypeIds = termsTypeIds;
        this.nickName = nickName;
        this.breadStyleId = breadStyleId;
    }
}
