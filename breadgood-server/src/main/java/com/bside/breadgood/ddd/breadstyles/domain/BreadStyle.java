package com.bside.breadgood.ddd.breadstyles.domain;


import com.bside.breadgood.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "unique_bread_style_name")})
public class BreadStyle extends BaseEntity {

    @Id
    @Column(name = "bread_style_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 이름
    @Column(nullable = false)
    private String name;
    // 설명글
    @Column(nullable = false)
    private String content;
    // 최애빵 스타일 설명 이미지
    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "contentImgUrl", nullable = false))
    private ImageUrl contentImgUrl;
    @Embedded
    @AttributeOverride(name = "imgUrl", column = @Column(name = "profileImgUrl", nullable = false))
    // 최애빵 스타일 프로필 이미지
    private ImageUrl profileImgUrl;
    // 최애빵 색상
    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false)
    private int sortNumber;

    @Builder
    public BreadStyle(String name, String content, String contentImgUrl, String profileImgUrl, String color, int sortNumber) {

        if (!StringUtils.hasText(name)) {
            throw new EmptyException("최애빵 스타일 이름이 없습니다.");
        }

        if (!StringUtils.hasText(content)) {
            throw new EmptyException("최애빵 스타일 설명이 없습니다.");
        }

        if (!StringUtils.hasText(contentImgUrl)) {
            throw new EmptyException("최애빵 스타일 설명 이미지가 없습니다.");
        }

        if (!StringUtils.hasText(profileImgUrl)) {
            throw new EmptyException("최애빵 스타일 프로필 이미지가 없습니다.");
        }

        if (!StringUtils.hasText(color)) {
            throw new EmptyException("최애빵 스타일 색상이 없습니다.");
        }

        this.name = name;
        this.content = content;
        this.contentImgUrl = ImageUrl.from(contentImgUrl);
        this.profileImgUrl = ImageUrl.from(profileImgUrl);
        this.color = color;
        this.sortNumber = sortNumber;
    }

    public String getContentImgUrl() {
        return contentImgUrl.getImgUrl();
    }

    public String getProfileImgUrl() {
        return profileImgUrl.getImgUrl();
    }
}
