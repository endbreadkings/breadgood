package com.bside.breadgood.jwt.application.exception;

public class AccessTokenExpiredException extends CustomJwtException {
    public AccessTokenExpiredException(String message) {
        super(message);
    }
}
