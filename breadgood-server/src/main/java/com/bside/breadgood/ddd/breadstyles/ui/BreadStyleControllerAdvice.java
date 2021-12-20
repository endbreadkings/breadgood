package com.bside.breadgood.ddd.breadstyles.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.breadstyles.application.exception.BreadStyleNotFoundException;
import com.bside.breadgood.ddd.users.application.exception.DuplicateUserNickNameException;
import com.bside.breadgood.ddd.users.application.exception.OnlySocialLinkException;
import com.bside.breadgood.ddd.users.application.exception.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class BreadStyleControllerAdvice extends ExceptionAdvice {

    public BreadStyleControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(BreadStyleNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse breadStyleNotFoundException(BreadStyleNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

}
