package com.bside.breadgood.ddd.users.domain;

import com.bside.breadgood.common.exception.WrongValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("닉네임 VO 테스트")
@Tag("VO")
class NickNameTest {

    @Test
    void 공백_일_경우() {
        assertThrows(WrongValueException.class, () -> {
            NickName.valueOf("닉 네임");
        });
    }

    @Test
    void 특수문자가_있는_경우() {
        assertThrows(WrongValueException.class, () -> {
            NickName.valueOf("닉%네임");
        });
    }

    @Test
    void 한_글자_이상_여덟_글자_미만인_경우() {
        final String nickName = "닉네임";
        final NickName nickNameVo = NickName.valueOf(nickName);
        assertEquals(nickNameVo.getNickName(), nickName);
    }

}