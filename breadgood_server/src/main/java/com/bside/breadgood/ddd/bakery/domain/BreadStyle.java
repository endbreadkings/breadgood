package com.bside.breadgood.ddd.bakery.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class BreadStyle {

    private String breadStyleName;
    private String breadStyleImgUrl;

    @Builder
    public BreadStyle(String breadStyleName, String breadStyleImgUrl) {
        this.breadStyleName = breadStyleName;
        this.breadStyleImgUrl = breadStyleImgUrl;
    }
}
