package com.bside.breadgood.common.vo;

import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.IllegalFileExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ImageUrl ValueObject 생성 테스트")
class ImageUrlTest {

    @ParameterizedTest(name = " \"{0}\" 빈값일 경우 오류가 발생한다.")
    @NullAndEmptySource
    void fromWithEmpty(String url) {
        assertThrows(EmptyException.class, () -> ImageUrl.from(url));
    }

    @ParameterizedTest(name = " \"{0}\" URL 형식이 아닐 경우 오류가 발생한다.")
    @ValueSource(strings = {"asd", "htt.resouser.com", "aaaskjkdfasdfadf.png"})
    void fromNotURL(String url) {
        assertThrows(IllegalImageUrlException.class, () -> ImageUrl.from(url));
    }

    @ParameterizedTest(name = " \"{0}\" 지원하지 않는 확장자 일 경우 오류가 발생한다.")
    @ValueSource(strings = {
            "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_off.pn",
            "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_off.afg"
    })
    void fromNotSupportExtension(String url) {
        assertThrows(IllegalFileExtensionException.class, () -> ImageUrl.from(url));
    }

    @ParameterizedTest(name = " \"{0}\" ImageUrl ValueObject 생성")
    @ValueSource(strings = {
            "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_off.png",
            "http://d74hbwjus7qtu.cloudfront.net/admin/case_2_off.svg",
            "http://d74hbwjus7qtu.cloudfront.net/admin/c121=asd+=123-%$#ase_2_off.svg",
            "http://d74hbwjus7qtu.cloudfront.net/adm_in/case_2_off.svg"
    })
    void from(String url) {
        assertDoesNotThrow(() -> ImageUrl.from(url));
    }
}
