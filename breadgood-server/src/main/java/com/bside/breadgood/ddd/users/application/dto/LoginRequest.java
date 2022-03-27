package com.bside.breadgood.ddd.users.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {
    @ApiModelProperty(example = "test@breadgood.com")
    private final String email;

    @ApiModelProperty(example = "1234")
    private final String password;

    public static LoginRequest valueOf(String email, String password) {
        return new LoginRequest(email, password);
    }

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
