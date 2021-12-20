package com.bside.breadgood.ddd.emoji.domain;

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
public class Emoji extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emoji_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false, unique = true)
    private int sortNumber;

    @Builder
    public Emoji(String name, String imgUrl, int sortNumber) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.sortNumber = sortNumber;
    }
}
