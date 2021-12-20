package com.bside.breadgood.authentication.exception;

import java.util.Arrays;

public class Oauth2NotProvideRedirectUriException extends RuntimeException {
    private final String[] args;

    public Oauth2NotProvideRedirectUriException(String... args) {
        super(Arrays.toString(args));
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
