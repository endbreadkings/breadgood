package com.bside.breadgood.ddd.bakerycategory.application.dto;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BakeryCategoryResponseDto {

    private Long id;
    private String title;
    private String titleColoredImgUrl;
    private String titleUncoloredImgUrl;
    private String color;
    private String markerImgUrl;
    private int sortNumber;
    private String content;

    public BakeryCategoryResponseDto(BakeryCategory entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.titleColoredImgUrl = entity.getTitleColoredImgUrl();
        this.titleUncoloredImgUrl = entity.getTitleUncoloredImgUrl();
        this.color = entity.getColor();
        this.markerImgUrl = entity.getMarkerImgUrl();
        this.sortNumber = entity.getSortNumber();
        this.content = entity.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BakeryCategoryResponseDto)) return false;

        BakeryCategoryResponseDto that = (BakeryCategoryResponseDto) o;

        if (getSortNumber() != that.getSortNumber()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getTitleColoredImgUrl() != null ? !getTitleColoredImgUrl().equals(that.getTitleColoredImgUrl()) : that.getTitleColoredImgUrl() != null)
            return false;
        if (getTitleUncoloredImgUrl() != null ? !getTitleUncoloredImgUrl().equals(that.getTitleUncoloredImgUrl()) : that.getTitleUncoloredImgUrl() != null)
            return false;
        if (getColor() != null ? !getColor().equals(that.getColor()) : that.getColor() != null) return false;
        if (getMarkerImgUrl() != null ? !getMarkerImgUrl().equals(that.getMarkerImgUrl()) : that.getMarkerImgUrl() != null)
            return false;
        return getContent() != null ? getContent().equals(that.getContent()) : that.getContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getTitleColoredImgUrl() != null ? getTitleColoredImgUrl().hashCode() : 0);
        result = 31 * result + (getTitleUncoloredImgUrl() != null ? getTitleUncoloredImgUrl().hashCode() : 0);
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + (getMarkerImgUrl() != null ? getMarkerImgUrl().hashCode() : 0);
        result = 31 * result + getSortNumber();
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }
}
