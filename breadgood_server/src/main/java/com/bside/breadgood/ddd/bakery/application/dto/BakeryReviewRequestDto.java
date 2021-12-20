package com.bside.breadgood.ddd.bakery.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BakeryReviewRequestDto {

    @ApiModelProperty(value = "빵집 리뷰 내용", example = "잉 너무 맛있는 걸욧??")
    private final String content;
    @ApiModelProperty(value = "빵집 리뷰 시그니처 메뉴", example = "[\"딸기크림케이크\", \"단팥빵\", \"카야소보로\"]")
    private final List<String> signatureMenus;

    @ApiModelProperty(value = "이모지 아이디", example = "1")
    private final Long emojiId;

    @Builder
    public BakeryReviewRequestDto(String content, Long emojiId, List<String> signatureMenus) {
        this.content = content;
        this.emojiId = emojiId;
        this.signatureMenus = signatureMenus;
    }
}
