package com.bside.breadgood.jwt.ui.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TokenRefreshResponse {
    private String accessToken;
    private Long accessTokenExpirationTimeMsec;
    private String refreshToken;
    private Long refreshTokenExpirationTimeMsec;
    private String tokenType = "Bearer";
}
