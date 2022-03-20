package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActiveTermsResponseDto {

    private Long typeId;

    private String name;

    private boolean required;

    private Long termsId;

    private String termsTypeLink;

    private String termsLink;

    public ActiveTermsResponseDto(TermsType termsType, Terms terms) {
        this.typeId = termsType.getId();
        this.name = termsType.getName();
        this.required = termsType.isRequired();
        this.termsId = terms.getId();
        this.termsTypeLink = String.format("/pages/termsType/%s", termsType.getId());
        this.termsLink = String.format("/pages/termsType/%s/terms/%s", termsType.getId(), terms.getId());
    }
}
