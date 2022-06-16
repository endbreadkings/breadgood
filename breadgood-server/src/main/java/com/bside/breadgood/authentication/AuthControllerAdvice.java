package com.bside.breadgood.authentication;

import com.bside.breadgood.authentication.exception.*;
import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.jwt.application.exception.AccessTokenExpiredException;
import com.bside.breadgood.jwt.application.exception.NotFoundRefreshToken;
import com.bside.breadgood.jwt.application.exception.RefreshTokenExpiredException;
import com.bside.breadgood.jwt.application.exception.TokenRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthControllerAdvice extends ExceptionAdvice {

    public AuthControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(OAuth2AlreadyRegisterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse oAuth2AlreadyRegisterException(OAuth2AlreadyRegisterException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse oAuth2NotFoundEmailException(OAuth2NotFoundEmailException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

    @ExceptionHandler(Oauth2NotProvideRedirectUriException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse oauth2NotProvideRedirectUriException(Oauth2NotProvideRedirectUriException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

    @ExceptionHandler(OAuth2UserNotSupportException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse oAuth2UserNotSupportException(OAuth2UserNotSupportException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }

}
