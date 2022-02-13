package com.bside.breadgood.ddd.users.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//FIXME Lombok 설정 수정 예정
public class LoginRequest {
    @ApiModelProperty(example = "test@breadgood.com")
    private String email;

    @ApiModelProperty(example = "1234")
    private String password;

    public static LoginRequest valueOf(String email, String password) {
        return new LoginRequest(email, password);
    }
}
