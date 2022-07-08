package com.bside.breadgood.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestLoggingHelper {
    public static void errorLogging(Logger logger) {
        errorLogging(logger, null);
    }

    public static void errorLogging(Logger logger, String errorMessage) {
        errorLogging(logger, errorMessage, null);

    }

    public static void errorLogging(Logger logger, String errorMessage, Exception exception) {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            logger.error("{} \n요청한 곳 :: {}, \n요청 API :: {}, \n파라미터 :: {} \n에러 ::",
                    errorMessage,
                    "알수없음",
                    "알수없음",
                    "알수없음",
                    exception
            );
        } else {
            final HttpServletRequest request = servletRequestAttributes.getRequest();

            logger.error("{} \n요청 헤더 :: {}, \n요청 API :: {}, \n파라미터 :: {} \nbody :: {} \n에러 ::",
                    errorMessage,
                    getHeaderInfo(request),
                    "[" + request.getMethod() + "]" + request.getRequestURI(),
                    getParamInfo(request),
                    getBodyInfo(request),
                    exception
            );
        }
    }

    private static String getBodyInfo(HttpServletRequest request) {
        try {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception ignored) {
        }
        return "없음";
    }

    private static StringBuilder getHeaderInfo(HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder paramsStringBuilder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            paramsStringBuilder.append(headerName).append(" = ").append(request.getHeader(headerName)).append(" ");
        }
        return paramsStringBuilder;
    }

    private static String getParamInfo(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        StringBuilder paramsStringBuilder = new StringBuilder();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            paramsStringBuilder.append(paramName).append(" = ").append(request.getParameter(paramName)).append(" ");
        }

        final String requestInfo = paramsStringBuilder.toString();
        return requestInfo.isEmpty() ? "없음" : requestInfo;
    }
}
