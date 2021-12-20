package com.bside.breadgood.jwt.ui.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRefreshResponse {
    private final String accessToken;
    private final Long accessTokenExpirationTimeMsec;
    private final String refreshToken;
    private final Long refreshTokenExpirationTimeMsec;
    private final String tokenType = "Bearer";

    @Builder
    public TokenRefreshResponse(String accessToken, Long accessTokenExpirationTimeMsec, String refreshToken, Long refreshTokenExpirationTimeMsec) {
        this.accessToken = accessToken;
        this.accessTokenExpirationTimeMsec = accessTokenExpirationTimeMsec;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTimeMsec = refreshTokenExpirationTimeMsec;
    }

}
