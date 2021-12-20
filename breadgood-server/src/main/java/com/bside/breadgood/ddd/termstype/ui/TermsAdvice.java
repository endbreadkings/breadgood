package com.bside.breadgood.ddd.termstype.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.termstype.application.excetion.IllegalTermsTypeException;
import com.bside.breadgood.ddd.termstype.application.excetion.NotChoiceRequireTermsTypeException;
import com.bside.breadgood.ddd.termstype.application.excetion.TermsNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TermsAdvice extends ExceptionAdvice {
    public TermsAdvice(MessageSource messageSource) {
        super(messageSource);
    }


    @ExceptionHandler(TermsNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse termsNotFoundException(TermsNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

    @ExceptionHandler(IllegalTermsTypeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse illegalTermsTypeException(IllegalTermsTypeException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(NotChoiceRequireTermsTypeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse notChoiceRequireTermsTypeException(NotChoiceRequireTermsTypeException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }


}
