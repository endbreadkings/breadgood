package com.bside.breadgood.jwt.ui;

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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class TokenControllerAdvice extends ExceptionAdvice {

    public TokenControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage()
                );
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ExceptionResponse accessTokenExpiredException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(NotFoundRefreshToken.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse notFoundRefreshTokenException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ExceptionResponse refreshTokenExpiredException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }


    //JWT 유효 기간 초과
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ExceptionResponse expiredJwtException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }


    //JWT 형식 불일치
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ExceptionResponse unsupportedJwtException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    //잘못된 JWT 구성
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ExceptionResponse malformedJwtException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    //JWT 서명 확인 불가
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse signatureException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

}
