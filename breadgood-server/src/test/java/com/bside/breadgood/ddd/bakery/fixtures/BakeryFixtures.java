package com.bside.breadgood.ddd.bakery.fixtures;

import com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto;
import com.bside.breadgood.ddd.bakery.domain.Bakery;

import java.util.List;

import static com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto.builder;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class BakeryFixtures {

    public static BakerySaveRequestDto 빵집등록요청(String city, Long bakeryCategoryId, Long emojiId, List<String> signatureMenus) {
        return builder()
                .title("서울")
                .city(city)
                .bakeryCategoryId(bakeryCategoryId)
                .description("")
                .content("빵을좋아하는사람만오십시오")
                .district("중분류위치값")
                .mapX(1D)
                .mapY(1D)
                .roadAddress("도로명주소값")
                .signatureMenus(signatureMenus)
                .emojiId(emojiId)
                .build();
    }

    public static final Bakery 빵집1 =
            new Bakery(
                    1L,
                    "1번 빵집",
                    "1번 빵집 설명입니다.",
                    1L,
                    null,
                    null,
                    1L
            );
}
