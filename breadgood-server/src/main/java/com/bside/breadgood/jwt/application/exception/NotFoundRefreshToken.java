package com.bside.breadgood.jwt.application.exception;

// database 에서 RefreshToken 을 발견하지 못했을 때
public class NotFoundRefreshToken extends CustomJwtException {

    private static final long serialVersionUID = 3L;

    public NotFoundRefreshToken(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
