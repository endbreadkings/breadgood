package com.bside.breadgood.ddd.bakery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignatureMenus {

    @ElementCollection
    private List<SignatureMenu> signatureMenus = new ArrayList<>();

    private SignatureMenus(List<SignatureMenu> signatureMenus) {
        this.signatureMenus = signatureMenus;
    }

    public static SignatureMenus valueOf(List<String> signatureMenus) {

        if (signatureMenus == null) {
            return null;
        }

        final List<SignatureMenu> reviewSignatureMenus = signatureMenus.stream()
                .map(SignatureMenu::valueOf)
                .collect(Collectors.toList());

        return new SignatureMenus(reviewSignatureMenus);
    }

    public int size() {
        return signatureMenus.size();
    }
}
