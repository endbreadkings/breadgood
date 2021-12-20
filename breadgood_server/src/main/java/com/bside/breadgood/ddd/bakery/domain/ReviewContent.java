package com.bside.breadgood.ddd.bakery.domain;


import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewContent {

    private final int minLength = 10;
    private final int maxLength = 500;

    private String content;

    private ReviewContent(String content) {

        validation(content);

        this.content = content;
    }

    public static ReviewContent valueOf(String content) {
        return new ReviewContent(content);
    }

    /**
     * 리뷰글은 최소 10글자 최대 500글자이다.
     */
    private void validation(String nickName) {


        if (StringUtils.isEmpty(nickName)) {
            throw new EmptyException("리뷰글이 비어 있습니다.");
        }

        if (nickName.length() < minLength || nickName.length() > maxLength) {
            throw new WrongValueException(String.format("리뷰글의 값이 잘못되었습니다. 최소 %s 글자 최대 %s 글자 [%s]", minLength, maxLength, nickName));
        }

    }
}
