package com.bside.breadgood.ddd.bakery.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakery.application.exception.BakeryNotFoundException;
import com.bside.breadgood.ddd.bakery.application.exception.DuplicateBakeryException;
import com.bside.breadgood.ddd.bakery.application.exception.IllegalCityException;
import com.bside.breadgood.ddd.bakery.application.exception.ReviewDeletionException;
import com.bside.breadgood.ddd.bakery.application.exception.ReviewNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


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