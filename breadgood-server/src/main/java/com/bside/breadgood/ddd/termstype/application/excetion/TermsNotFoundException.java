package com.bside.breadgood.ddd.termstype.application.excetion;

import lombok.Getter;

@Getter
public class TermsNotFoundException extends RuntimeException {


    private String[] args;

    public TermsNotFoundException(String... args) {
        this.args = args;
    }

    public TermsNotFoundException(String message) {
        super(message);
    }

}
