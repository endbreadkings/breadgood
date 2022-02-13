package com.bside.breadgood.ddd.users.application.dto;

import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.users.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String profileImgUrl;
    private final String nickName;
    private final Long breadStyleId;

    public UserResponseDto(User user, BreadStyleResponseDto breadStyleResponseDto) {
        this.id = user.getId();
        this.profileImgUrl = breadStyleResponseDto.getProfileImgUrl();
        this.nickName = user.getNickName();
        this.breadStyleId = user.getBreadStyle();
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.profileImgUrl = null;
        this.nickName = user.getNickName();
        this.breadStyleId = user.getBreadStyle();
    }
}
