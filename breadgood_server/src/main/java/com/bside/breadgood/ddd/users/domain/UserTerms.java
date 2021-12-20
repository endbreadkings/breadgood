package com.bside.breadgood.ddd.users.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Embeddable
public class UserTerms {

    private LocalDateTime termsDate;
    private boolean termsAgree;
    private long termsType;


    @Builder
    public UserTerms(LocalDateTime termsDate, boolean termsAgree, long termsType) {
        this.termsDate = termsDate;
        this.termsAgree = termsAgree;
        this.termsType = termsType;
    }
}
