package com.bside.breadgood.ddd.bakerycategory.application.dto;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import lombok.Getter;

@Getter
public class BakeryCategoryResponseDto {

    private final Long id;
    private final String title;
    private final String titleActiveImgUrl;
    private final String titleInactiveImgUrl;
    private final String color;
    private final String makerImgUrl;
    private final int sortNumber;

    public BakeryCategoryResponseDto(BakeryCategory entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.titleActiveImgUrl = entity.getTitleActiveImgUrl();
        this.titleInactiveImgUrl = entity.getTitleInactiveImgUrl();
        this.color = entity.getColor();
        this.makerImgUrl = entity.getMarkerImgUrl();
        this.sortNumber = entity.getSortNumber();
    }
}
