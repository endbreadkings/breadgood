package com.bside.breadgood.ddd.emoji.application.exception;

import lombok.Getter;

@Getter
public class EmojiNotFoundException extends RuntimeException {

    private String[] args;

    public EmojiNotFoundException(String... args) {
        this.args = args;
    }

    public EmojiNotFoundException(String message) {
        super(message);
    }
}
