package com.bside.breadgood.ddd.bakery.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CheckDuplicateBakeryResponseDto {

    private final boolean idDuplicate;
    private final String nickName;

    public CheckDuplicateBakeryResponseDto(boolean idDuplicate, String nickName) {
        this.idDuplicate = idDuplicate;
        this.nickName = nickName;
    }

}
