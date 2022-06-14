package com.bside.breadgood.fixtures.bakeryreview;

import com.bside.breadgood.ddd.bakery.application.dto.BakeryReviewRequestDto;
import org.assertj.core.util.Lists;

/**
 * author : haedoang
 * date : 2022/06/15
 * description :
 */
public class BakeryReviewFixture {
    public static BakeryReviewRequestDto 리뷰등록요청(Long emojiId) {
        return BakeryReviewRequestDto.builder()
                .content("정말정말진짜최고너무나도맛있어서 둘이먹다가 하나가죽어도모를맛이래요")
                .emojiId(emojiId)
                .signatureMenus(Lists.newArrayList("곰보빵", "소라빵", "고로케"))
                .build();
    }
}
