package com.bside.breadgood.ddd.users.application.exception;

import lombok.Getter;


@Getter
public class IllegalRoleException extends RuntimeException {
    private String[] args;

    public IllegalRoleException(String... args) {
        this.args = args;
    }

    public IllegalRoleException(String message) {
        super(message);
    }
}
