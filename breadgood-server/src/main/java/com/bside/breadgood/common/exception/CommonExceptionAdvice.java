package com.bside.breadgood.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.mail.AuthenticationFailedException;
import java.util.List;


@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public ExceptionResponse validationExceptionHandler(ValidationException e) {
        log.error(e.getMessage(), e);
        List<FieldError> errors = e.getErrors();
        return new ExceptionResponse(400, e.getMessage(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ExceptionResponse badRequestHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class})
    public ExceptionResponse bindExceptionHandler(BindException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {AuthenticationFailedException.class, HttpClientErrorException.Unauthorized.class})
    public ExceptionResponse unauthorizedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ExceptionResponse methodNotAllowedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(405, "지원하지 않는 API 입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {EmptyException.class})
    public ExceptionResponse internalServerErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(500, e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {WrongValueException.class})
    public ExceptionResponse wrongValueExceptionHandler(WrongValueException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(500, e.getMessage());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = {Exception.class})
//    public ExceptionResponse internalServerErrorHandler(Exception e) {
//        log.error(e.getMessage(), e);
//        return new ExceptionResponse(500, "Internal server error");
//    }

    /**
     * http status: 400
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[BaseException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage(), e);
        return new ExceptionResponse(400, e.getMessage());
    }

}
