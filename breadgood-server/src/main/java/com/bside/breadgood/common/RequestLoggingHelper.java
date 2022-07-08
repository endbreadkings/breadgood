package com.bside.breadgood.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestLoggingHelper {
    public static void errorLogging(Logger logger, HttpServletRequest request) {
        errorLogging(logger, null, request, null);
    }

    public static void errorLogging(Logger logger, String errorMessage, HttpServletRequest request) {
        errorLogging(logger, errorMessage, request, null);

    }

    public static void errorLogging(Logger logger, String errorMessage, HttpServletRequest request, Exception exception) {
        Enumeration<String> params = request.getParameterNames();
        StringBuilder paramsStringBuilder = new StringBuilder();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            paramsStringBuilder.append(paramName).append(" = ").append(request.getParameter(paramName));
        }

        logger.error("{} \n요청한 곳 :: {}, \n요청 API :: {}, \n파라미터 :: {} \n에러 ::",
                errorMessage,
                request.getHeader("Referer"),
                "[" + request.getMethod() + "]" + request.getRequestURI() + "?" + request.getQueryString(),
                paramsStringBuilder.toString().isEmpty() ? "없음" : paramsStringBuilder,
                exception
        );

    }

}
