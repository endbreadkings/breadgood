package com.bside.breadgood.ddd.users.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.termstype.application.excetion.IllegalTermsTypeException;
import com.bside.breadgood.ddd.termstype.application.excetion.NotChoiceRequireTermsTypeException;
import com.bside.breadgood.ddd.users.application.exception.DuplicateUserNickNameException;
import com.bside.breadgood.ddd.users.application.exception.OnlySocialLinkException;
import com.bside.breadgood.ddd.users.application.exception.UserNotFoundException;
import com.bside.breadgood.jwt.application.exception.AccessTokenExpiredException;
import com.bside.breadgood.jwt.application.exception.NotFoundRefreshToken;
import com.bside.breadgood.jwt.application.exception.RefreshTokenExpiredException;
import com.bside.breadgood.jwt.application.exception.TokenRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UserControllerAdvice extends ExceptionAdvice {

    public UserControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(DuplicateUserNickNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse duplicateUserNickNameException(DuplicateUserNickNameException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(OnlySocialLinkException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse onlySocialLinkException(OnlySocialLinkException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse userNotFoundException(UserNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }


}
