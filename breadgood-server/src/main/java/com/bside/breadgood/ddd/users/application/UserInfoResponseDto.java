package com.bside.breadgood.ddd.users.application;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private final Long id;
    private final String nickName;
    private final String profileImgUrl;
    private final Long breadStyleId;
    private final String breadStyleName;
    private final String breadStyleColor;
    private final boolean isWithdrawal;


    @Builder
    public UserInfoResponseDto(Long userId, String nickName, String profileImgUrl, Long breadStyleId, String breadStyleName, String breadStyleColor, boolean isWithdrawal) {
        this.id = userId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.breadStyleId = breadStyleId;
        this.breadStyleName = breadStyleName;
        this.breadStyleColor = breadStyleColor;
        this.isWithdrawal = isWithdrawal;
    }
}
