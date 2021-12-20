package com.bside.breadgood.ddd.emoji.application.dto;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class EmojiResponseDto {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int sortNumber;


    public EmojiResponseDto(Emoji entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.imgUrl = entity.getImgUrl();
        this.sortNumber = entity.getSortNumber();
    }
}
