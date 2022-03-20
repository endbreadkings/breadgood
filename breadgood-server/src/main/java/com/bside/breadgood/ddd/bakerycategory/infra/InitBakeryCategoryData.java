package com.bside.breadgood.ddd.bakerycategory.infra;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;

import java.util.HashSet;
import java.util.Set;

public class InitBakeryCategoryData {


    Set<BakeryCategory> data = new HashSet<>();

    {

        add("음료&빵",
                "https://d74hbwjus7qtu.cloudfront.net/admin/cate1_blue.svg",
                "https://d74hbwjus7qtu.cloudfront.net/admin/cate1_white.svg",
                "#4579FF",
                "https://d74hbwjus7qtu.cloudfront.net/admin/pin_on.png",
                "커피&차와 함께 빵을\n즐길수 있는 베이커리 카페",
                1);
        add("빵에집중",
                "https://d74hbwjus7qtu.cloudfront.net/admin/cate2_yellow.svg",
                "https://d74hbwjus7qtu.cloudfront.net/admin/cate2_white.svg",
                "#FEBE52",
                "https://d74hbwjus7qtu.cloudfront.net/admin/pin_be_off.png",
                "빵을 전문적으로 파는\n일반 베이커리",
                2);

    }

    public Set<BakeryCategory> get() {
        return data;
    }

    private void add(String title, String titleColoredImgUrl, String titleUncoloredImgUrl, String color,
                     String markerImgUrl, String content, int sortNumber) {
        data.add(BakeryCategory.builder()
                .title(title)
                .titleColoredImgUrl(titleColoredImgUrl)
                .titleUncoloredImgUrl(titleUncoloredImgUrl)
                .color(color)
                .markerImgUrl(markerImgUrl)
                .content(content)
                .sortNumber(sortNumber)
                .build());
    }
}
