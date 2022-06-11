package com.bside.breadgood.fixtures.bakery;

import com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import org.assertj.core.util.Lists;

import java.util.List;

import static com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto.builder;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class BakeryFixture {
    public static BakerySaveRequestDto 빵집1_등록요청(Long bakeryCategoryId, Long emojiId) {
      return BakerySaveRequestDto.builder()
              .title("빵집1")
              .city("서울특별시")
              .bakeryCategoryId(bakeryCategoryId)
              .description("설명은 10글자 이상이어야 합니다")
              .content("빵을좋아하는사람만오십시오")
              .district("중분류위치값")
              .mapX(1D)
              .mapY(1D)
              .roadAddress("도로명주소값")
              .signatureMenus(Lists.newArrayList("1", "2", "3"))
              .emojiId(emojiId)
              .build();
    }

    public static BakerySaveRequestDto 빵집2_등록요청(Long bakeryCategoryId, Long emojiId) {
        return BakerySaveRequestDto.builder()
                .title("빵집2")
                .city("서울특별시")
                .bakeryCategoryId(bakeryCategoryId)
                .description("설명은 10글자 이상이어야 합니다")
                .content("빵을좋아하는사람만오십시오")
                .district("중분류위치값")
                .mapX(1D)
                .mapY(1D)
                .roadAddress("도로명주소값")
                .signatureMenus(Lists.newArrayList("1", "2", "3"))
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
