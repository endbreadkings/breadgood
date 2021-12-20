package com.bside.breadgood.ddd.termstype.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private Long id;

    // 약관 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 약관 적용일자
    @Column(nullable = false)
    private LocalDate executionDate;


    @Builder
    public Terms(String content, LocalDate executionDate) {
        this.content = content;
        this.executionDate = executionDate;
    }
}
