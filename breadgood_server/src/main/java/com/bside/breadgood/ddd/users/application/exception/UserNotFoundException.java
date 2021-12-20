package com.bside.breadgood.ddd.users.application.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private String[] args;

    public UserNotFoundException(String... args) {
        this.args = args;
    }

    public UserNotFoundException() {
        // super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
