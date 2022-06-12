package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TermsTypeResponseDto {

    private final Long id;
    private final String name;
    private final boolean required;

    @Builder
    public TermsTypeResponseDto(Long id, String name, boolean required) {
        this.id = id;
        this.name = name;
        this.required = required;
    }

    public static TermsTypeResponseDto valueOf(TermsType termsType) {
        return new TermsTypeResponseDto(termsType.getId(), termsType.getName(), termsType.isRequired());
    }
}
