package com.bside.breadgood.authentication.exception;

import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

public class OAuth2NotFoundEmailException extends AuthenticationException {

    private final String[] args;

    public OAuth2NotFoundEmailException(String... args) {
        super(Arrays.toString(args));
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
