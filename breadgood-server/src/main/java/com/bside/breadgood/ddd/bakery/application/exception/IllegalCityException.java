package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

@Getter
public class IllegalCityException extends RuntimeException {

    private final String[] args;

    public IllegalCityException(String... args) {
        this.args = args;
    }

}
