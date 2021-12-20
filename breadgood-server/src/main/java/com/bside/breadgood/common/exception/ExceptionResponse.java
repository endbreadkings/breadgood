package com.bside.breadgood.common.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;


@Getter
public class ExceptionResponse {
    private final int code;
    private Date timestamp;
    private final String message;
    private List<FieldError> errors;

    public ExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(int code, String message, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ExceptionResponse(int code, Date timestamp, String message, List<FieldError> errors) {
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }

    public ExceptionResponse(int code, Date timestamp, String message) {
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
    }
}
