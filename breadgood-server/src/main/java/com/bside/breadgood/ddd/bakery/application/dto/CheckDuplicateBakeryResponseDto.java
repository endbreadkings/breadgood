package com.bside.breadgood.ddd.bakery.application.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CheckDuplicateBakeryResponseDto {

    private final boolean idDuplicate;
    private final String nickName;
    private final Long bakeryId;

    public CheckDuplicateBakeryResponseDto(boolean idDuplicate, String nickName, Long bakeryId) {
        this.idDuplicate = idDuplicate;
        this.nickName = nickName;
        this.bakeryId = bakeryId;
    }

}
