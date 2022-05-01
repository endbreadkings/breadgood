package com.bside.breadgood.ddd.termstype.ui.dto;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsSaveRequestDto {
    private Long termsTypeId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate executionDate;

    private TermsSaveRequestDto(Long termsTypeId, String content, LocalDate executionDate) {
        this.termsTypeId = termsTypeId;
        this.content = content;
        this.executionDate = executionDate;
    }

    public static TermsSaveRequestDto valueOf(Long termsTypeId, String content, LocalDate executionDate) {
        return new TermsSaveRequestDto(termsTypeId, content, executionDate);
    }

    public Terms toEntity() {
        return Terms.builder()
                .content(content)
                .executionDate(executionDate)
                .build();
    }
}
