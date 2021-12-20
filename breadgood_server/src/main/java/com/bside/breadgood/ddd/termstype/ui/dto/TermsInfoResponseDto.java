package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
public class TermsInfoResponseDto {

    private final String executionDate;
    private final String content;

    public TermsInfoResponseDto(Terms terms) {
        final LocalDate executionDate = terms.getExecutionDate();
        final String content = terms.getContent();
        this.content = content;
        this.executionDate = executionDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

}
