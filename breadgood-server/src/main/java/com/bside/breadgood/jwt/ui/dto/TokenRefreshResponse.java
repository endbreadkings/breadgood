package com.bside.breadgood.jwt.ui.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//FIXME Lombok 설정 수정 예정 & tokenType 객체화할지 고민 예정(사용성 검토)
public class TokenRefreshResponse {
    private String accessToken;
    private Long accessTokenExpirationTimeMsec;
    private String refreshToken;
    private Long refreshTokenExpirationTimeMsec;
    private String tokenType = "Bearer";
}
