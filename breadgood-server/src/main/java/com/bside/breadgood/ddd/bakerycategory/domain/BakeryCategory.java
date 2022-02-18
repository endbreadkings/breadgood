package com.bside.breadgood.ddd.bakerycategory.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import com.bside.breadgood.common.vo.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BakeryCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakery_category_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "titleColoredImgUrl", nullable = false))
    private ImageUrl titleColoredImgUrl;

    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "titleWhiteImgUrl", nullable = false))
    private ImageUrl titleWhiteImgUrl;

    @Column(nullable = false)
    private String color;

    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "markerImgUrl", nullable = false))
    private ImageUrl markerImgUrl;

    @Column(nullable = false)
    private int sortNumber;

    @Builder
    public BakeryCategory(String title, String content, String titleColoredImgUrl,
        String titleWhiteImgUrl, String color, String markerImgUrl, int sortNumber) {
        this.title = title;
        this.content = content;
        this.titleColoredImgUrl = ImageUrl.from(titleColoredImgUrl);
        this.titleWhiteImgUrl = ImageUrl.from(titleWhiteImgUrl);
        this.color = color;
        this.markerImgUrl = ImageUrl.from(markerImgUrl);
        this.sortNumber = sortNumber;
    }

    public String getTitleColoredImgUrl() {
        return titleColoredImgUrl.getImgUrl();
    }

    public String getTitleWhiteImgUrl() {
        return titleWhiteImgUrl.getImgUrl();
    }

    public String getMakerImgUrl() {
        return markerImgUrl.getImgUrl();
    }
}
