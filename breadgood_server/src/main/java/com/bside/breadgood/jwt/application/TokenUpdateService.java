package com.bside.breadgood.jwt.application;

import com.bside.breadgood.jwt.application.exception.NotFoundRefreshToken;
import com.bside.breadgood.jwt.application.exception.RefreshTokenExpiredException;
import com.bside.breadgood.jwt.application.exception.TokenRefreshException;
import com.bside.breadgood.jwt.domain.RefreshToken;
import com.bside.breadgood.jwt.infra.RefreshTokenRepository;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshRequest;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import com.bside.breadgood.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(TokenUpdateService.class);

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;


    @Value("${app.auth.access-token.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${app.auth.refresh-token.secret}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${app.auth.refresh-token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException(token.getToken(), "Refresh token was expired. Please make a new signIn request");
        }
    }

    /**
     * <span>accessToken, refreshToken 을 재발급한다. </span>
     */
    @Transactional
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {

        final String requestRefreshToken = request.getRefreshToken();
        final RefreshToken fetchedRefreshToken = findByToken(requestRefreshToken).orElseThrow(() -> new NotFoundRefreshToken(requestRefreshToken, "Refresh token is not in database!"));

        // refresh validate
        verifyExpiration(fetchedRefreshToken);

        String refreshToken = requestRefreshToken;

        // 리프레시 토큰도 발급 해야하는지 확인
        if (verifyCreateRefreshToken(fetchedRefreshToken)) {
            refreshToken = updateRefreshToken(fetchedRefreshToken);
        }

        final Long userIdByToken = refreshTokenService.getUserIdByToken(refreshToken);
        final String accessToken = accessTokenService.createTokenById(userIdByToken);

        final Long accessTokenExpirationTimeMsec = accessTokenService.getExpirationTime(accessToken);
        final Long refreshTokenExpirationTimeMsec = refreshTokenService.getExpirationTime(refreshToken);

        return TokenRefreshResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpirationTimeMsec(accessTokenExpirationTimeMsec)
                .refreshTokenExpirationTimeMsec(refreshTokenExpirationTimeMsec)
                .refreshToken(refreshToken)
                .build();
    }

    private String updateRefreshToken(RefreshToken refreshToken) {
        final String token = JwtUtils.createToken(Long.toString(refreshToken.getUser()), REFRESH_TOKEN_EXPIRATION, REFRESH_TOKEN_SECRET_KEY);
        return refreshToken.updateToken(token);
    }

    // refresh token 이 access token 만료시간보다 더 적게 남아 있을 경우, refresh token 재발급 해야함.
    private boolean verifyCreateRefreshToken(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().compareTo(LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION)) < 0;
    }

    protected Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
