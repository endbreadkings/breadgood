package com.bside.breadgood.ddd.emoji.application.dto;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
