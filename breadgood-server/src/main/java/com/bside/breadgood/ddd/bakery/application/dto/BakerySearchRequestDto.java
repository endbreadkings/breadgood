package com.bside.breadgood.ddd.bakery.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
public class BakerySearchRequestDto {

    @ApiModelProperty(position = 1, dataType = "String", example = "서울특별시", notes = "필터가 없을 땐 빈값 넣어주세요.")
    private String city;

    @ApiModelProperty(position = 2, dataType = "String", example = "강서구", notes = "필터가 없을 땐 빈값 넣어주세요.")
    private String district;

    @ApiModelProperty(position = 3, dataType = "Set", example = "[1, 2]", notes = "필터를 적용하고 싶지 않을 떈 빈 배열을 넣어주세요.")
    private Set<Long> bakeryCategories;

    @Builder
    public BakerySearchRequestDto(String city, String district, Set<Long> bakeryCategories) {
        this.city = city;
        this.district = district;
        this.bakeryCategories = bakeryCategories;
    }
}
