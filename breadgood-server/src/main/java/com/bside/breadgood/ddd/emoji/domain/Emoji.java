package com.bside.breadgood.ddd.emoji.domain;

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
public class Emoji extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emoji_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ImageUrl imgUrl;

    @Column(nullable = false, unique = true)
    private int sortNumber;

    @Builder
    public Emoji(String name, String imgUrl, int sortNumber) {
        this.name = name;
        this.imgUrl = ImageUrl.from(imgUrl);
        this.sortNumber = sortNumber;
    }

    public String getImgUrl() {
        return this.imgUrl.getImgUrl();
    }
}
