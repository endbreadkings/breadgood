package com.bside.breadgood.ddd.bakery.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FirstBakeryRegisterUserInfo {

    private final String nickName;
    private final String profileImgUrl;

    @Builder
    public FirstBakeryRegisterUserInfo(String nickName, String profileImgUrl) {
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
    }
}
