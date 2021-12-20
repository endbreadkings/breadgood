package com.bside.breadgood.ddd.bakerycategory.application.exception;

import lombok.Getter;

@Getter
public class BakeryCategoryNotFoundException extends RuntimeException {

    private String[] args;

    public BakeryCategoryNotFoundException(String... args) {
        this.args = args;
    }

    public BakeryCategoryNotFoundException(String message) {
        super(message);
    }
}
