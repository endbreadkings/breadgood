package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ActiveTermsResponseDto {

    private final Long typeId;

    private final String name;

    private final boolean required;

    private final Long termsId;

    private final String termsTypeLink;

    private final String termsLink;

    public ActiveTermsResponseDto(TermsType termsType, Terms terms) {
        this.typeId = termsType.getId();
        this.name = termsType.getName();
        this.required = termsType.isRequired();
        this.termsId = terms.getId();
        this.termsTypeLink = String.format("/pages/termsType/%s", termsType.getId());
        this.termsLink = String.format("/pages/termsType/%s/terms/%s", termsType.getId(), terms.getId());
    }
}
