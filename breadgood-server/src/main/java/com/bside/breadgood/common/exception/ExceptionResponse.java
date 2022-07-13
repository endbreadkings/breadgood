package com.bside.breadgood.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;


@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {
    private int code;
    private Date timestamp;
    private String message;
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
