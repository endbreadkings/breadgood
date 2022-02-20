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
    @AttributeOverride(name = "imgUrl", column = @Column(name = "titleUncoloredImgUrl", nullable = false))
    private ImageUrl titleUncoloredImgUrl;

    @Column(nullable = false)
    private String color;

    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "markerImgUrl", nullable = false))
    private ImageUrl markerImgUrl;

    @Column(nullable = false)
    private int sortNumber;

    @Builder
    public BakeryCategory(String title, String content, String titleColoredImgUrl,
        String titleUncoloredImgUrl, String color, String markerImgUrl, int sortNumber) {
        this.title = title;
        this.content = content;
        this.titleColoredImgUrl = ImageUrl.from(titleColoredImgUrl);
        this.titleUncoloredImgUrl = ImageUrl.from(titleUncoloredImgUrl);
        this.color = color;
        this.markerImgUrl = ImageUrl.from(markerImgUrl);
        this.sortNumber = sortNumber;
    }

    public String getTitleColoredImgUrl() {
        return titleColoredImgUrl.getImgUrl();
    }

    public String getTitleUncoloredImgUrl() {
        return titleUncoloredImgUrl.getImgUrl();
    }

    public String getMakerImgUrl() {
        return markerImgUrl.getImgUrl();
    }
}
