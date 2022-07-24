package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsTypeInfoResponseDto {
    private Long id;
    private String title;
    private List<TermsInfoResponseDto> termsList;

    public TermsTypeInfoResponseDto(TermsType termsType) {
        this.id = termsType.getId();
        this.title = termsType.getName();
        this.termsList = termsType.getTerms().stream()
                .filter(terms -> terms.getExecutionDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Terms::getExecutionDate).reversed())
                .map(TermsInfoResponseDto::new)
                .collect(Collectors.toList());
    }
}
