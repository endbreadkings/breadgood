package com.bside.breadgood.common.exception;

public class IllegalFileExtensionException extends IllegalArgumentException {
    public IllegalFileExtensionException(String errorMessage) {
        super(errorMessage);
    }
}
