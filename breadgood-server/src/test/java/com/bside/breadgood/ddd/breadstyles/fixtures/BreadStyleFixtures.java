package com.bside.breadgood.ddd.breadstyles.fixtures;

import com.bside.breadgood.common.vo.ImageUrl;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class BreadStyleFixtures {
    public static BreadStyle 빵스타일1() {
        return new BreadStyle(
                1L,
                "담백",
                "담백빵 내용",
                ImageUrl.from("https://test.domain.com/path1/img.png"),
                ImageUrl.from("https://test.domain.com/path1/img.png"),
                "#FFFFFF");
    }
}
