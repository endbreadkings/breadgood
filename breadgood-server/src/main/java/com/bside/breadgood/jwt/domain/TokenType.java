package com.bside.breadgood.jwt.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * author : haedoang
 * date : 2022/02/17
 * description :
 */
public enum TokenType {
    BEARER("Bearer");

    private String name;

    TokenType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getValue() {
        return name;
    }
}
