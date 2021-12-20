package com.bside.breadgood.ddd.bakery.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Point {

    private Double mapX;
    private Double mapY;

    @Builder
    public Point(Double mapX, Double mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }
}
