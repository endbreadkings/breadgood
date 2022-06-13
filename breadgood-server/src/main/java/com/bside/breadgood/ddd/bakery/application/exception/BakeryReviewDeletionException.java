package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

/**
 * author : haedoang
 * date : 2022/06/13
 * description :
 */
@Getter
public class BakeryReviewDeletionException extends RuntimeException {
    private String[] args;

    public BakeryReviewDeletionException(String... args) {
        this.args = args;
    }

    public BakeryReviewDeletionException(String message) {
        super(message);
    }
}
