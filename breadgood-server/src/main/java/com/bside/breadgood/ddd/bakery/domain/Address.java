package com.bside.breadgood.ddd.bakery.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    // 시
    private String city;

    // 구
    private String district;

    // 도로명
    private String roadAddress;

    @Builder
    public Address(String city, String district, String roadAddress) {
        this.city = city;
        this.district = district;
        this.roadAddress = roadAddress;
    }
}
