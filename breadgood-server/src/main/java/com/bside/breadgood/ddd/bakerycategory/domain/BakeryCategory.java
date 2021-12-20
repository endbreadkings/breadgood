package com.bside.breadgood.ddd.bakerycategory.domain;

import com.bside.breadgood.common.domain.BaseEntity;
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

    @Column(nullable = false)
    private String titleImgUrl;

    @Column(nullable = false)
    private String markerImgUrl;

    @Column(nullable = false)
    private int sortNumber;

    @Builder
    public BakeryCategory(String title, String titleImgUrl, String markerImgUrl, String content, int sortNumber) {
        this.title = title;
        this.titleImgUrl = titleImgUrl;
        this.markerImgUrl = markerImgUrl;
        this.content = content;
        this.sortNumber = sortNumber;
    }
}
