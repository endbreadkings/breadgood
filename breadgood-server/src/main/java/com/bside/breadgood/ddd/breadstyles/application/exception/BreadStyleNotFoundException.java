package com.bside.breadgood.ddd.breadstyles.application.exception;

import lombok.Getter;

@Getter
public class BreadStyleNotFoundException extends RuntimeException {
    private String[] args;

    public BreadStyleNotFoundException(String... args) {
        this.args = args;
    }

    public BreadStyleNotFoundException(String message) {
        super(message);
    }
}
