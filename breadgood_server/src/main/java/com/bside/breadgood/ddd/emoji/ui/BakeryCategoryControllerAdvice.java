package com.bside.breadgood.ddd.emoji.ui;

import com.bside.breadgood.common.exception.ExceptionAdvice;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.emoji.application.exception.EmojiNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

public class BakeryCategoryControllerAdvice extends ExceptionAdvice {

    public BakeryCategoryControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(EmojiNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse emojiNotFoundException(EmojiNotFoundException ex, WebRequest request) {
        String messagePath = super.getMessagePathByMyMethodName();
        return super.getExceptionResponse(request, messagePath, ex.getArgs());
    }


}
