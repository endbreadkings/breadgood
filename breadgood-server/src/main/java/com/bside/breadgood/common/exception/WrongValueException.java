package com.bside.breadgood.common.exception;

public class WrongValueException extends RuntimeException{
    public WrongValueException(String format) {
        super(format);
    }
}

