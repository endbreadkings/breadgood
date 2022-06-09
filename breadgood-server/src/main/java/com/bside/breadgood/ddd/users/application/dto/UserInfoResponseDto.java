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
            .isWithdrawal(false)
            .build();

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
