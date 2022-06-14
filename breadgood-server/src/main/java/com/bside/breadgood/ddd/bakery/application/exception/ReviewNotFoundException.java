package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

@Getter
public class ReviewNotFoundException extends RuntimeException{
    private String[] args;

    public ReviewNotFoundException(String... args) {
        this.args = args;
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}