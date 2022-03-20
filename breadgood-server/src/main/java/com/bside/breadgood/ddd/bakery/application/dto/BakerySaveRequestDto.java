package com.bside.breadgood.ddd.bakery.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BakerySaveRequestDto {

    @ApiModelProperty(value = "빵집 이름", example = "루브레드")
    private final String title;

    @ApiModelProperty(value = "도로명 주소", example = "서울 강서구 화곡로64길 70")
    private final String roadAddress;

    @ApiModelProperty(value = "X좌표", example = "1.41211704222904E7")
    private final Double mapX;

    @ApiModelProperty(value = "Y좌표", example = "4516889.7719205")
    private final Double mapY;

    // review
    @ApiModelProperty(value = "빵집 리뷰 내용", example = "잉 너무 맛있는 걸욧??")
    private final String content;

    @ApiModelProperty(value = "빵집 리뷰 시그니처 메뉴", example = "[\"딸기크림케이크\", \"단팥빵\", \"카야소보로\"]")
    private final List<String> signatureMenus;

    @ApiModelProperty(value = "주소 설명", example = "")
    private final String description;

    @ApiModelProperty(value = "큰 단위", example = "서울특별시")
    private final String city;

    @ApiModelProperty(value = "중간 단위", example = "강서구")
    private final String district;

    @ApiModelProperty(value = "빵집 카테고리 아이디", example = "1")
    private final Long bakeryCategoryId;

    @ApiModelProperty(value = "이모지 아이디", example = "1")
    private final Long emojiId;

    @Builder
    public BakerySaveRequestDto(
            String title,
            String description,
            String city,
            String district,
            String roadAddress,
            Double mapX,
            Double mapY,
            Long bakeryCategoryId,
            String content,
            Long emojiId,
            List<String> signatureMenus
    ) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.district = district;
        this.roadAddress = roadAddress;
        this.mapX = mapX;
        this.mapY = mapY;
        this.bakeryCategoryId = bakeryCategoryId;
        this.content = content;
        this.emojiId = emojiId;
        this.signatureMenus = signatureMenus;
    }

}
