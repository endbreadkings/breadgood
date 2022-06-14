package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

@Getter
public class BakeryNotFoundException extends RuntimeException {
    private Object[] args;

    public BakeryNotFoundException(String... args) {
        this.args = args;
    }

    public BakeryNotFoundException(Object message) {
        super(message.toString());
    }
}