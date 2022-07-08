package com.bside.breadgood.common;

import com.bside.breadgood.common.exception.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.bside.breadgood.common.RequestLoggingHelper.errorLogging;


@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger("requestDefaultLogger");
    private static final ObjectMapper om = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    @Before("@within(org.springframework.web.bind.annotation.RestController))")
    public void requestLoggingRestController(JoinPoint joinPoint) {
        var logData = new RequestLoggingType(joinPoint);
        logger.info("{}", toJson(logData));
    }

    @Before(value = "@within(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionHandler(JoinPoint joinPoint) {

        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            if (joinPoint.getArgs()[i] instanceof Exception) {
                Exception exception = (Exception) joinPoint.getArgs()[i];
                errorLogging(logger, exception.getMessage(), exception);
            }
        }
    }

    @AfterReturning(value = "@within(org.springframework.web.bind.annotation.RestController))", returning = "returnValue")
    public void responseLoggingRestController(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        var logData = new ResponseLoggingType(joinPoint, returnValue);
        logger.info("{}", toJson(logData));
    }

    @AfterThrowing(value = "@within(org.springframework.web.bind.annotation.RestController))", throwing = "throwable")
    public void throwingLoggingRestController(JoinPoint joinPoint, Throwable throwable) {
        logger.error("{}", toJson(new ThrowingLoggingType(joinPoint, throwable)));
    }

    @Getter
    @ToString
    private static class RequestLoggingType {
        private final String type;
        private final String executeType;
        private final String executeSimpleType;
        private final String executeMethod;

        private final Map<String, Object> params;


        public RequestLoggingType(JoinPoint joinPoint) {
            final var signature = joinPoint.getSignature();
            final var declaringTypeName = signature.getDeclaringTypeName();

            this.type = "request";
            this.executeType = declaringTypeName;
            this.executeSimpleType = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1);
            this.executeMethod = signature.getName();
            this.params = params(joinPoint);
        }
    }

    @Getter
    @ToString
    private static class ResponseLoggingType {
        private final String type;
        private final String executeType;
        private final String executeSimpleType;
        private final String executeMethod;
        private final Object result;
        private final String resultData;

        public ResponseLoggingType(JoinPoint joinPoint, Object commonResponse) {
            final var signature = joinPoint.getSignature();
            final var declaringTypeName = signature.getDeclaringTypeName();

            this.type = "response";
            this.executeType = declaringTypeName;
            this.executeSimpleType = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1);
            this.executeMethod = signature.getName();
            this.result = commonResponse;
            this.resultData = StringUtils.abbreviate(toJson(commonResponse), 2000);
        }
    }

    @Getter
    @ToString
    private static class ThrowingLoggingType {
        private final String type;
        private final String executeType;
        private final String executeSimpleType;
        private final String executeMethod;
        private final String message;

        private final Map<String, Object> params;

        public ThrowingLoggingType(JoinPoint joinPoint, Throwable throwable) {
            final var signature = joinPoint.getSignature();
            final var declaringTypeName = signature.getDeclaringTypeName();
            this.type = "throwing";
            this.executeType = declaringTypeName;
            this.executeSimpleType = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1);
            this.executeMethod = signature.getName();
            this.message = throwable.getMessage();
            this.params = null;
        }
    }

    private static Map<String, Object> params(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }

    public static String toJson(Object object) {
        if (object == null) return "";

        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
