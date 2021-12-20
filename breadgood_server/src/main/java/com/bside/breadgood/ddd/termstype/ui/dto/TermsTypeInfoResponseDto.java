package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class TermsTypeInfoResponseDto {

    private final String title;
    private final List<TermsInfoResponseDto> termsList;

    public TermsTypeInfoResponseDto(TermsType termsType) {
        this.title = termsType.getName();
        this.termsList = termsType.getTerms().stream()
                .filter(terms -> terms.getExecutionDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Terms::getExecutionDate).reversed())
                .map(TermsInfoResponseDto::new)
                .collect(Collectors.toList());
    }
}
