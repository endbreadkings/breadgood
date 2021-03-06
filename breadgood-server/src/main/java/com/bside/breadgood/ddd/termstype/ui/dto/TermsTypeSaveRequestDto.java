package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsTypeSaveRequestDto {

    private String name;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate executionDate;

    private boolean required;

    private TermsTypeSaveRequestDto(String name, String content, LocalDate executionDate, boolean required) {
        this.name = name;
        this.content = content;
        this.executionDate = executionDate;
        this.required = required;
    }

    public static TermsTypeSaveRequestDto valueOf(String name, String content, LocalDate executionDate, boolean required) {
        return new TermsTypeSaveRequestDto(name, content, executionDate, required);
    }

    public TermsType toEntity(int sortNumber) {
        return TermsType.builder()
                .name(name)
                .content(content)
                .executionDate(executionDate)
                .required(required)
                .sortNumber(sortNumber)
                .build();
    }
}
