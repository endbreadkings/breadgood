package com.bside.breadgood.ddd.breadstyles.ui.dto;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import lombok.Getter;

@Getter
public class BreadStyleResponseDto {
    private final Long id;
    //  이름
    private final String name;
    // 설명글
    private final String content;
    // 설명 이미지
    private final String imgUrl;
    // 프로필 이미지
    private final String profileImgUrl;
    // 최애빵 색상
    private String color;

    public BreadStyleResponseDto(BreadStyle entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.content = entity.getContent();
        this.imgUrl = entity.getImgUrl();
        this.profileImgUrl = entity.getProfileImgUrl();
        this.color = entity.getColor();
    }
}
