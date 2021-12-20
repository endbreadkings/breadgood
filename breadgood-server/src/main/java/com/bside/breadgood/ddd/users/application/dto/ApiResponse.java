package com.bside.breadgood.ddd.users.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {
    private boolean success;
    private String message;

    @Builder
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}