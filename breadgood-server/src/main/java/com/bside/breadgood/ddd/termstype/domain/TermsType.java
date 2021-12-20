package com.bside.breadgood.ddd.termstype.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TermsType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_type_id")
    private Long id;

    // 약관 항목 제목
    @Column(nullable = false)
    private String name;

    // 약관 필수 여부
    @Column(nullable = false)
    private boolean required;

    // 약관 정렬 순서
    @Column(nullable = false)
    private int sortNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "terms_type_id")
    private Set<Terms> terms = new HashSet<>();


    @Builder
    public TermsType(String name, String content, LocalDate executionDate, boolean required, int sortNumber) {
        this.name = name;
        this.required = required;
        this.sortNumber = sortNumber;
        this.addTerms(Terms.builder()
                .content(content)
                .executionDate(executionDate)
                .build());
    }

    public void addTerms(Terms terms) {
        this.terms.add(terms);
    }


}
