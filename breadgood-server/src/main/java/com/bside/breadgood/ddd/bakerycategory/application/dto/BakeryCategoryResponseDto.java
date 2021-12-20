package com.bside.breadgood.ddd.bakerycategory.application.dto;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import lombok.Getter;

@Getter
public class BakeryCategoryResponseDto {

    private final Long id;
    private final String title;
    private final String titleImgUrl;
    private final String makerImgUrl;
    private final int sortNumber;

    public BakeryCategoryResponseDto(BakeryCategory entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.titleImgUrl = entity.getTitleImgUrl();
        this.makerImgUrl = entity.getMarkerImgUrl();
        this.sortNumber = entity.getSortNumber();
    }
}
