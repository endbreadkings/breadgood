package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class TermsDetailResponseDto {

    private final String title;
    private final String executionDate;
    private final String content;

    public TermsDetailResponseDto(TermsType termsType, Terms terms) {
        this.title = termsType.getName();
        final LocalDate executionDate = terms.getExecutionDate();
        final String content = terms.getContent();
        this.content = content;
        this.executionDate = executionDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

    }
}
