package com.bside.breadgood.ddd.breadstyles.ui.dto;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import lombok.Getter;

import java.util.Objects;

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
        this.imgUrl = entity.getContentImgUrl();
        this.profileImgUrl = entity.getProfileImgUrl();
        this.color = entity.getColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BreadStyleResponseDto that = (BreadStyleResponseDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(imgUrl, that.imgUrl)) return false;
        if (!Objects.equals(profileImgUrl, that.profileImgUrl))
            return false;
        return Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (profileImgUrl != null ? profileImgUrl.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
