package com.bside.breadgood.ddd.users.domain;

import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022/02/16
 * description :
 */
class EmailTest {

    @Test
    @DisplayName("이메일 도메인 생성")
    public void create() {
        // given
        final Email actual = Email.valueOf("tester@xmail.com");

        // then
        assertThat(actual.getEmail()).isEqualTo("tester@xmail.com");
    }

    @ParameterizedTest(name = "이메일 유효성 검사: " + ParameterizedTest.ARGUMENTS_PLACEHOLDER)
    @NullSource
    @EmptySource
    public void validateEmailNullAndEmpty(String candidate) {
        //then
        assertThatThrownBy(() -> Email.valueOf(candidate)).isInstanceOf(EmptyException.class)
                .hasMessageContaining("이메일이 비어있습니다.");
    }

    @ParameterizedTest(name = "이메일 유효성 검사: " + ParameterizedTest.ARGUMENTS_PLACEHOLDER)
    @ValueSource(strings = {"test.ac.kr", "test@ac", "@.."})
    public void validateEmailString(String candidate) {
        //then
        assertThatThrownBy(() -> Email.valueOf(candidate)).isInstanceOf(WrongValueException.class)
                .hasMessageContaining("이메일이 잘못되었습니다.");
    }
}