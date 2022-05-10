package com.bside.breadgood.ddd.users.domain;


import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private final static String EMAIL_PATTERN = "^(.+)@([A-z].+[A-z])$";

    private String email;

    private Email(String email) {

        this.email = validation(email);
    }

    public static Email valueOf(String email) {
        return new Email(email);
    }

    private String validation(String email) {

        if (StringUtils.isEmpty(email)) {
            throw new EmptyException("이메일이 비어있습니다.");
        }

        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new WrongValueException(String.format("이메일이 잘못되었습니다. [xxx@xxxx.com] [%s]", email));
        }

        return email;
    }

}
