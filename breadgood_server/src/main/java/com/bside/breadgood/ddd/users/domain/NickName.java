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
public class NickName {
    private final static String NICK_NAME_PATTERN = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$";

    private String nickName;

    private NickName(String nickName) {

        final String trimNickName = nickName = nickName.replace("\\s", "").trim();

        validation(trimNickName);

        this.nickName = nickName;
    }

    public static NickName valueOf(String nickName) {
        return new NickName(nickName);
    }

    /**
     * 별명은 최소 1글자 최대 7글자이내 한글 또는 영문이어야 한다(8글자 초과 불가능)
     * 별명에 특수 문자 사용 불가능, 띄어쓰기 불가능
     */
    private void validation(String nickName) {

        if (StringUtils.isEmpty(nickName)) {
            throw new EmptyException("닉네임이 비어 있습니다.");
        }

        if (nickName.length() < 1 || nickName.length() > 8) {
            throw new WrongValueException(String.format("닉네임이 잘못되었습니다. 최소 1글자 최대 7글자 [%s]", nickName));
        }

        if (!Pattern.matches(NICK_NAME_PATTERN, nickName)) {
            throw new WrongValueException(String.format("닉네임이 잘못되었습니다. 공백이나 특수 문자가 포함되어 있습니다. [%s]", nickName));
        }

    }
}
