package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BakeryResponseDto {

    @ApiModelProperty(value = "최애 빵집 최초 등록자 ")
    private UserInfoResponseDto userInfoResponseDto;
    @ApiModelProperty(value = "빵집 이름", example = "루브레드")
    private String title;
    @ApiModelProperty(value = "빵집 카테고리 이름", example = "빵에집중")
    private String categoryTitle;
    @ApiModelProperty(value = "도로명 주소", example = "서울 강서구 화곡로64길 70")
    private String roadAddress;
    @ApiModelProperty(value = "리뷰 등록 상세 이미지들")
    private List<BakeryReviewResponseDto> bakeryReviews = new ArrayList<>();

    @Builder
    public BakeryResponseDto(UserInfoResponseDto userInfoResponseDto, String title, String categoryTitle, String roadAddress, List<BakeryReviewResponseDto> bakeryReviews) {
        this.userInfoResponseDto = userInfoResponseDto;
        this.title = title;
        this.categoryTitle = categoryTitle;
        this.roadAddress = roadAddress;
        this.bakeryReviews.addAll(bakeryReviews);
    }
}
