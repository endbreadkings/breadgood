package com.bside.breadgood.jwt.application;

import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.domain.AuthProvider;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.jwt.application.exception.AccessTokenExpiredException;
import com.bside.breadgood.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessTokenService implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);
    private final UserRepository userRepository;

    @Value("${app.auth.access-token.secret}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${app.auth.access-token.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    public String createTokenByAuthentication(Authentication authentication) {

        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            final Map<String, Object> attributes = oidcUser.getAttributes();

            final String sub = (String) attributes.get("sub");
            final String iss = ((URL) attributes.get("iss")).getHost();

            if (!iss.contains("apple")) {
                throw new IllegalArgumentException("애플로그인만 가능합니다.");
            }
            final User user = userRepository.findBySocialLink(AuthProvider.apple, sub).orElseThrow(IllegalArgumentException::new);
            return JwtUtils.createToken(Long.toString(user.getId()), ACCESS_TOKEN_EXPIRATION, ACCESS_TOKEN_SECRET_KEY);

        } else {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return JwtUtils.createToken(Long.toString(userPrincipal.getId()), ACCESS_TOKEN_EXPIRATION, ACCESS_TOKEN_SECRET_KEY);
        }

    }

    public String createTokenById(Long userId) {
        return JwtUtils.createToken(Long.toString(userId), ACCESS_TOKEN_EXPIRATION, ACCESS_TOKEN_SECRET_KEY);
    }

    public Long getUserIdByToken(String token) {
        return Long.parseLong(JwtUtils.getSubjectByToken(token, ACCESS_TOKEN_SECRET_KEY));
    }

    public Long getExpirationTime(String token) {
        return JwtUtils.getExpirationTime(token, ACCESS_TOKEN_SECRET_KEY);
    }


    public boolean validate(String token) {
        try {
            return JwtUtils.validateAccessToken(token, ACCESS_TOKEN_SECRET_KEY);
        } catch (ExpiredJwtException ex) {
            System.out.println("만료된 토큰" + token);
            throw new AccessTokenExpiredException(ex.getMessage());
        }
    }

}
