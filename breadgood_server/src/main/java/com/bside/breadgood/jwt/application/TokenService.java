package com.bside.breadgood.jwt.application;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String createTokenByAuthentication(Authentication authentication);

    Long getUserIdByToken(String token);

    boolean validate(String token);
}
