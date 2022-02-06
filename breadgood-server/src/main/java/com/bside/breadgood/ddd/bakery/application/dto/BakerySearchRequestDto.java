package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.application.enums.BakerySortType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
public class BakerySearchRequestDto {

    @ApiModelProperty(position = 1, dataType = "String", example = "서울특별시", notes = "필터가 없을 땐 빈값 넣어주세요.")
    private final String city;

    @ApiModelProperty(position = 2, dataType = "String", example = "강서구", notes = "필터가 없을 땐 빈값 넣어주세요.")
    private final String district;

    @ApiModelProperty(position = 3, dataType = "Set", example = "[1, 2]", notes = "필터를 적용하고 싶지 않을 떈 빈 배열을 넣어주세요.")
    private final Set<Long> bakeryCategories;

    @ApiModelProperty(position = 4, dataType = "Enum", example = "ID_DESC", notes = "파라미터를 보내지 않으면(null일 경우) ID_DESC 타입으로 정렬됩니다.")
    private final BakerySortType bakerySortType;

    @Builder
    public BakerySearchRequestDto(String city, String district, Set<Long> bakeryCategories, BakerySortType bakerySortType) {
        this.city = city;
        this.district = district;
        this.bakeryCategories = bakeryCategories;
        this.bakerySortType = bakerySortType;
    }
}
