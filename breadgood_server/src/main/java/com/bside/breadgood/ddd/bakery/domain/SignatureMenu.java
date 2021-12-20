package com.bside.breadgood.ddd.bakery.domain;

import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignatureMenu {

    private String signatureMenu;
    private final int maxLength = 15;

    private SignatureMenu(String signatureMenu) {
        if (StringUtils.isEmpty(signatureMenu)) {
            throw new EmptyException("시그니처 메뉴 값이 없습니다.");
        }

        if (signatureMenu.length() > maxLength) {
            throw new WrongValueException(String.format("시그니처 메뉴는 최대 %s 글자 까지 가능합니다.", maxLength));
        }

        this.signatureMenu = signatureMenu;
    }

    public static SignatureMenu valueOf(String signatureMenu) {
        return new SignatureMenu(signatureMenu);
    }
}
