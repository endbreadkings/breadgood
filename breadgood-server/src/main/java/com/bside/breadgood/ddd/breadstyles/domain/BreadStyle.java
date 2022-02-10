package com.bside.breadgood.ddd.breadstyles.domain;


import com.bside.breadgood.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BreadStyle extends BaseEntity {

    @Id
    @Column(name = "bread_style_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 이름
    private String name;
    // 설명글
    private String content;
    // 최애빵 스타일 설명 이미지
    private String imgUrl;
    // 최애빵 스타일 프로필 이미지
    private String profileImgUrl;
    // 최애빵 색상
    private String color;

    @Builder
    public BreadStyle(String name, String content, String imgUrl, String profileImgUrl, String color) {
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.profileImgUrl = profileImgUrl;
        this.color = color;
    }
}
