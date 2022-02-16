package com.bside.breadgood.jwt.ui.dto;

import com.bside.breadgood.jwt.domain.TokenType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRefreshResponse {
    private String accessToken;
    private Long accessTokenExpirationTimeMsec;
    private String refreshToken;
    private Long refreshTokenExpirationTimeMsec;
    private TokenType tokenType;

    @Builder
    public TokenRefreshResponse(String accessToken, Long accessTokenExpirationTimeMsec, String refreshToken, Long refreshTokenExpirationTimeMsec) {
        this.accessToken = accessToken;
        this.accessTokenExpirationTimeMsec = accessTokenExpirationTimeMsec;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTimeMsec = refreshTokenExpirationTimeMsec;
        this.tokenType = TokenType.BEARER;
    }

    public String getTokenType() {
        return tokenType.getValue();
    }
}
