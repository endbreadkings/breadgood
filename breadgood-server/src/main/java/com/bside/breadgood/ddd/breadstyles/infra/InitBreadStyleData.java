package com.bside.breadgood.ddd.breadstyles.infra;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;

import java.util.HashSet;
import java.util.Set;

public class InitBreadStyleData {

    Set<BreadStyle> data = new HashSet<>();

    {

        add("크림", "생크림빵, 슈크림빵, \n" +
                        "맘모스빵 케이크 등 \n" +
                        "크림이 가득 든 빵", "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_cream_.png",
                "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_cream.png",
                "#fd9573");
        add("달콤", "단팥빵, 연유브레드,\n" +
                        "시나몬롤 등 \n" +
                        "크림이 없는 달콤한 빵", "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_sweet_.png",
                "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_sweet.png",
                "#c59577");
        add("짭짤", "피자빵, 고로케,양파빵, \n" +
                        "마늘바게트 등 \n" +
                        "짭짤한 맛의 조리빵", "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_salty_.png",
                "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_salty.png",
                "#f3b54c");
        add("담백", "식빵, 바게트, 치아바타, \n" +
                        "크루아상, 베이글 등\n" +
                        "자극적이지 않은 담백한 빵", "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png",
                "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png",
                "#70a9ff");


    }

    public Set<BreadStyle> get() {
        return data;
    }

    private void add(String name, String content, String contentImgUrl, String profileImgUrl, String color) {
        data.add(new BreadStyle(name, content, contentImgUrl, profileImgUrl, color));
    }
}
