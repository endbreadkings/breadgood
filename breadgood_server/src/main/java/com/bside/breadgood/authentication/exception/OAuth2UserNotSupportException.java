package com.bside.breadgood.authentication.exception;

import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

public class OAuth2UserNotSupportException extends AuthenticationException {

    private final String[] args;

    public OAuth2UserNotSupportException(String... args) {
        super(Arrays.toString(args));
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

}
