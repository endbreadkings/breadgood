package com.bside.breadgood.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * @author doyoung
 * 예외 처리 핸들러 클래스
 * getMessagePathByMyMethodName 를 호출한 메소드이름을 바탕으로 exception_**.yml 에 있는 메시지를 찾아서
 * ExceptionResponse 를 만들어 반환합니다.
 */
@Slf4j
public class ExceptionAdvice {

    private final MessageSource messageSource;

    public ExceptionAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String EXCEPTION = "Exception";
    private static final String DOT = ".";

    public String getMessagePathByMyMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName.substring(0, methodName.indexOf(EXCEPTION)) + DOT;
    }

    public ExceptionResponse getExceptionResponse(WebRequest request, String messagePath) {
        int code = getErrorCode(messagePath);
        String message = getErrorMessage(messagePath);

        log.error("message :: {} , request :: {} , code :: {}", message, request, code);

        return new ExceptionResponse(
                code,
                new Date(),
                message);
    }

    public ExceptionResponse getExceptionResponse(WebRequest request, String messagePath, String... args) {
        int code = getErrorCode(messagePath);
        String message = getErrorMessage(messagePath, args);

        log.error("message :: {} , request :: {} , code :: {}, args :: {}", message, request, code, args);

        return new ExceptionResponse(
                code,
                new Date(),
                message);
    }


    private String getErrorMessage(String messagePath) {
        return getPropertyMessage(messagePath + MSG);
    }

    private String getErrorMessage(String messagePath, String... args) {
        return getPropertyMessage(messagePath + MSG, args);
    }


    private int getErrorCode(String messagePath) {
        String code = getPropertyMessage(messagePath + CODE);
        return Integer.parseInt(code);
    }

    // 설정 정보에 해당하는 메시지를 조회합니다.
    private String getPropertyMessage(String path) {
        return messageSource.getMessage(path, null, LocaleContextHolder.getLocale());
    }

    // path 정보, 추가 argument 로 현재 locale 에 맞는 메시지를 조회합니다.
    private String getPropertyMessage(String path, Object[] args) {
        return messageSource.getMessage(path, args, LocaleContextHolder.getLocale());
    }

}
