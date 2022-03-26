package com.bside.breadgood.ddd.bakery.bakerycategory.fixtures;

import com.bside.breadgood.common.vo.ImageUrl;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class BakeryCategoryFixtures {
    public static final BakeryCategory 카테고리1 = new BakeryCategory(
            1L,
            "카테고리1",
            "카테고리1 설명입니다.",
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            "#FFFFFF",
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            1
    );

    public static final BakeryCategory 카테고리2 = new BakeryCategory(
            2L,
            "카테고리2",
            "카테고리2 설명입니다.",
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            "#000000",
            ImageUrl.from("https://test.breadgood.com/path1/img.png"),
            2
    );
}
