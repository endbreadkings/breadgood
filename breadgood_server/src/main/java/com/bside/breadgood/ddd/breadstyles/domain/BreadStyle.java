package com.bside.breadgood.ddd.breadstyles.domain;


import com.bside.breadgood.common.domain.BaseEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
//@EqualsAndHashCode(of = "id", callSuper = false)
public class BreadStyle extends BaseEntity {

    @Id
    @Column(name = "bread_style_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //  이름
    private String name;
    // 설명글
    private String content;
    // 설명 이미지
    private String imgUrl;

    @Column(nullable = true)
    private String profileImgUrl;

    @Builder
    public BreadStyle(String name, String content, String imgUrl, String profileImgUrl) {
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.profileImgUrl = profileImgUrl;
    }
}
