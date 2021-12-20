package com.bside.breadgood.ddd.users.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    @Builder
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}