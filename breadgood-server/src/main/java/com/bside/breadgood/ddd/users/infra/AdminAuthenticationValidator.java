package com.bside.breadgood.ddd.users.infra;

import com.bside.breadgood.ddd.users.application.dto.LoginRequest;
import com.bside.breadgood.ddd.users.application.exception.IllegalRoleException;
import com.bside.breadgood.ddd.users.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * author : haedoang
 * date : 2022/04/25
 * description : 관리자 계정 인증을 검증합니다.
 */
@Component
@RequiredArgsConstructor
public class AdminAuthenticationValidator {
    private final AuthenticationManager authenticationManager;

    private void validate(Authentication auth) {
        if (unauthorized(auth)) {
            throw new IllegalRoleException();
        }
    }

    private boolean unauthorized(Authentication auth) {
        return auth.getAuthorities()
                .stream()
                .noneMatch(authority ->
                        authority.getAuthority().equals(Role.ADMIN.getKey())
                );
    }

    public Authentication validate(LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        validate(authentication);
        return authentication;
    }
}
