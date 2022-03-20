package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TermsTypeSaveRequestDto {

    private final String name;

    private final String content;

    private final LocalDate executionDate;

    private final boolean required;

    private final int sortNumber;


    private TermsTypeSaveRequestDto(String name, String content, LocalDate executionDate, boolean required, int sortNumber) {
        this.name = name;
        this.content = content;
        this.executionDate = executionDate;
        this.required = required;
        this.sortNumber = sortNumber;
    }

    public static TermsTypeSaveRequestDto valueOf(String name, String content, LocalDate executionDate, boolean required, int sortNumber) {
        return new TermsTypeSaveRequestDto(name, content, executionDate, required, sortNumber);
    }

    public TermsType toEntity() {
        return TermsType.builder()
                .name(name)
                .content(content)
                .executionDate(executionDate)
                .required(required)
                .sortNumber(sortNumber)
                .build();
    }
}
