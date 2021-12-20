package com.bside.breadgood.ddd.bakerycategory.infra;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;

import java.util.HashSet;
import java.util.Set;

public class InitBakeryCategoryData {


    Set<BakeryCategory> data = new HashSet<>();

    {

        add("음료&빵", "https://d74hbwjus7qtu.cloudfront.net/admin/tb1.png", "https://d74hbwjus7qtu.cloudfront.net/admin/pin_on.png", "커피&차와 함께 빵을\n 즐길수 있는 베이커리 카페",1);
        add("빵에집중", "https://d74hbwjus7qtu.cloudfront.net/admin/tb2.png", "https://d74hbwjus7qtu.cloudfront.net/admin/pin_be_off.png", "빵을 전문적으로 파는\n 일반 베이커리 ",2);

    }

    public Set<BakeryCategory> get() {
        return data;
    }

    private void add(String title, String titleImgUrl, String markerImgUrl, String content, int sortNumber) {
        data.add(new BakeryCategory(title, titleImgUrl, markerImgUrl, content, sortNumber));
    }
}
