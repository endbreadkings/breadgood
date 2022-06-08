package com.bside.breadgood.ddd.users.application.dto;

import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.users.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String profileImgUrl;
    private String nickName;
    private Long breadStyleId;

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

    public static UserResponseDto getDefault() {
        return new DefaultUserResponseDto();
    }


    static class DefaultUserResponseDto extends UserResponseDto {
        DefaultUserResponseDto() {
            // TODO
        }
    }


}
