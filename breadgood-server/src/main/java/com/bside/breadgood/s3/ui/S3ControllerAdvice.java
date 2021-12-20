package com.bside.breadgood.s3.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
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
public class S3ControllerAdvice extends ExceptionAdvice {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    public S3ControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(S3UploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse s3UploadException(Exception ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse maxUploadSizeExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, maxRequestSize);
    }



}