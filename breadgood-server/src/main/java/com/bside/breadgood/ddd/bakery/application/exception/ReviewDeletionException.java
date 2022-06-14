package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

/**
 * author : haedoang
 * date : 2022/06/13
 * description :
 */
@Getter
public class ReviewDeletionException extends RuntimeException {
    private String[] args;

    public ReviewDeletionException(String... args) {
        this.args = args;
    }

    public ReviewDeletionException(Object message) {
        super(message.toString());
    }
}
