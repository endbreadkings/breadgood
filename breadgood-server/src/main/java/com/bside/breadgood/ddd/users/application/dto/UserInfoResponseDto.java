package com.bside.breadgood.ddd.users.application.dto;

import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.users.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    public static final UserInfoResponseDto DEFAULT_USER_INFO_RESPONSE_DTO = UserInfoResponseDto.builder()
            .breadStyleId(null)
            .breadStyleName(null)
            .userId(0L)
            .nickName("빵긋")
            .breadStyleColor("#696972")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_off.png")
            .withdrawal(false)
            .build();

    private Long id;
    private String nickName;
    private String profileImgUrl;
    private Long breadStyleId;
    private String breadStyleName;
    private String breadStyleColor;
    private boolean withdrawal;

    @Builder
    public UserInfoResponseDto(Long userId, String nickName, String profileImgUrl, Long breadStyleId, String breadStyleName, String breadStyleColor, boolean withdrawal) {
        this.id = userId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.breadStyleId = breadStyleId;
        this.breadStyleName = breadStyleName;
        this.breadStyleColor = breadStyleColor;
        this.withdrawal = withdrawal;
    }

    public static UserInfoResponseDto valueOf(BreadStyleResponseDto breadStyleResponseDto, User user) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getNickName(),
                breadStyleResponseDto.getProfileImgUrl(),
                breadStyleResponseDto.getId(),
                breadStyleResponseDto.getName(),
                breadStyleResponseDto.getColor(),
                true
        );
    }

    public static UserInfoResponseDto getDefault() {
        return DEFAULT_USER_INFO_RESPONSE_DTO;
    }
}
