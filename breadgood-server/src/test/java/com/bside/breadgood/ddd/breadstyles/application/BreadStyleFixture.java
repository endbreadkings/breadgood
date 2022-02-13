package com.bside.breadgood.ddd.breadstyles.application;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;

public class BreadStyleFixture {
    public static final BreadStyle 담백 = BreadStyle.builder()
            .content("식빵, 바게트, 치아바타, \n" +
                    "크루아상, 베이글 등\n" +
                    "자극적이지 않은 담백한 빵")
            .name("담백")
            .color("#8FBCFF")
            .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png")
            .build();


    public static final BreadStyle 짭짤 = BreadStyle.builder()
            .content("피자빵, 고로케,양파빵, \n" +
                    "마늘바게트 등 \n" +
                    "짭짤한 맛의 조리빵")
            .name("짭짤")
            .color("#FFBC4A")
            .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_salty_.png")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_salty.png")
            .build();
}
