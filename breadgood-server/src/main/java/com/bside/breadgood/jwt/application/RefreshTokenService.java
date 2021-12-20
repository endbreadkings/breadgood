package com.bside.breadgood.jwt.application;

import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.ddd.users.domain.AuthProvider;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.jwt.application.exception.RefreshTokenExpiredException;
import com.bside.breadgood.jwt.domain.RefreshToken;
import com.bside.breadgood.jwt.infra.RefreshTokenRepository;
import com.bside.breadgood.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${app.auth.refresh-token.secret}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${app.auth.refresh-token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;


    @Transactional
    public String createTokenByAuthentication(Authentication authentication) {

        Long userId;
        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            final Map<String, Object> attributes = oidcUser.getAttributes();

            final String sub = (String) attributes.get("sub");
            final String iss = ((URL) attributes.get("iss")).getHost();

            if (!iss.contains("apple")) {
                throw new IllegalArgumentException("애플로그인만 가능합니다.");
            }
            final User user = userRepository.findBySocialLink(AuthProvider.apple, sub).orElseThrow(IllegalArgumentException::new);
            userId = user.getId();

        } else {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            userId = userPrincipal.getId();
        }

        final String token = JwtUtils.createToken(Long.toString(userId), REFRESH_TOKEN_EXPIRATION, REFRESH_TOKEN_SECRET_KEY);
        final LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION);

        final UserResponseDto user = userService.findById(userId);

        final RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(expirationDate)
                .token(token)
                .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public Long getUserIdByToken(String token) {
        return Long.parseLong(JwtUtils.getSubjectByToken(token, REFRESH_TOKEN_SECRET_KEY));
    }

    public Long getExpirationTime(String token) {
        return JwtUtils.getExpirationTime(token, REFRESH_TOKEN_SECRET_KEY);
    }


    public boolean validate(String token) {
        try {
            return JwtUtils.validateAccessToken(token, REFRESH_TOKEN_SECRET_KEY);
        } catch (ExpiredJwtException ex) {
            throw new RefreshTokenExpiredException(ex.getMessage());
        }
    }


}
