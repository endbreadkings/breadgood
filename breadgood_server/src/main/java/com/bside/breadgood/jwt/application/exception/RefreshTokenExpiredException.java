package com.bside.breadgood.jwt.application.exception;

public class RefreshTokenExpiredException extends CustomJwtException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
