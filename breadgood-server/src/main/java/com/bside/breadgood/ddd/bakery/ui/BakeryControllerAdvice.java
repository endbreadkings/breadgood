package com.bside.breadgood.ddd.bakery.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakery.application.exception.*;
import com.bside.breadgood.s3.application.exception.S3UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BakeryControllerAdvice extends ExceptionAdvice {


    public BakeryControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(BakeryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse bakeryNotFoundException(BakeryNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getMessage());
    }


    @ExceptionHandler(DuplicateBakeryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse duplicateBakeryException(DuplicateBakeryException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }


    @ExceptionHandler(IllegalCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse illegalCityException(IllegalCityException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

    @ExceptionHandler(ReviewDeletionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse reviewDeletionException(ReviewDeletionException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getMessage());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse reviewNotFoundException(ReviewNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getMessage());
    }
}