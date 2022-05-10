package com.bside.breadgood.fixtures.breadstyle;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;

public class BreadStyleFixture {
    public static final BreadStyle 크림 = BreadStyle.builder()
            .name("크림")
            .content("생크림빵, 슈크림빵, \n" +
                    "맘모스빵 케이크 등 \n" +
                    "크림이 가득 든 빵")
            .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_cream_.png")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_cream.png")
            .color("#fd9573")
            .sortNumber(100)
            .build();


    public static final BreadStyle 달콤 = BreadStyle.builder()
            .name("달콤")
            .content("단팥빵, 연유브레드,\n" +
                    "시나몬롤 등 \n" +
                    "크림이 없는 달콤한 빵")
            .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_sweet_.png")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_sweet.png")
            .color("#c59577")
            .sortNumber(200)
            .build();

    public static final BreadStyle 짭짤 = BreadStyle.builder()
        .content("피자빵, 고로케,양파빵, \n" +
            "마늘바게트 등 \n" +
            "짭짤한 맛의 조리빵")
        .name("짭짤")
        .color("#FFBC4A")
        .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_salty_.png")
        .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_salty.png")
        .sortNumber(300)
        .build();

    public static final BreadStyle 담백 = BreadStyle.builder()
            .content("식빵, 바게트, 치아바타, \n" +
                    "크루아상, 베이글 등\n" +
                    "자극적이지 않은 담백한 빵")
            .name("담백")
            .color("#8FBCFF")
            .contentImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png")
            .profileImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png")
            .sortNumber(400)
            .build();
}
