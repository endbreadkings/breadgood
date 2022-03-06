package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.bakery.domain.BakeryReview;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.users.application.UserInfoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class BakerySearchResponseDto {

    @ApiModelProperty(example = "1", notes = "빵집 아이디")
    private final Long id;

    @ApiModelProperty(value = "빵집 이름", example = "루브레드")
    private final String title;

    @ApiModelProperty(value = "도로명 주소", example = "서울 강서구 화곡로64길 70")
    private final String roadAddress;

    @ApiModelProperty(value = "X좌표", example = "1.41211704222904E7")
    private final Double mapX;
    @ApiModelProperty(value = "Y좌표", example = "4516889.7719205")
    private final Double mapY;

    @ApiModelProperty(value = "빵집 등록자 아이디", example = "3")
    private final Long userId;
    @ApiModelProperty(value = "빵집 카드 프로필 이미지", example = "https://d74hbwjus7qtu.cloudfront.net/admin/bc2.png\"")
    private final String profileImgUrl;
    @ApiModelProperty(value = "빵집 카드 빵 스타일 이름", example = "\"크림\"")
    private final String breadStyleName;

    @ApiModelProperty(value = "빵집 카드 빵 스타일 색상", example = "\"#EEEEEE\"")
    private final String breadStyleColor;
    @ApiModelProperty(value = "빵집 카드 닉네임", example = "테스트유저2")
    private final String nickName;

    // 카테고리
    @ApiModelProperty(value = "빵집 카테고리 이름", example = "빵에집중")
    private final String categoryTitle;
    @ApiModelProperty(value = "빵집 카테고리 아이콘 이미지", example = "https://d74hbwjus7qtu.cloudfront.net/admin/cate1_blue.svg")
    private final String categoryImgUrl;

    // review
    @ApiModelProperty(value = "빵집 리뷰 내용", example = "잉 너무 맛있는 걸욧??")
    private final String content;
    @ApiModelProperty(value = "빵집 리뷰 시그니처 메뉴", example = "[\"딸기크림케이크\", \"단팥빵\", \"카야소보로\"]")
    private final List<String> signatureMenus;

    public BakerySearchResponseDto(Bakery bakery, BakeryReview bakeryReview, BakeryCategoryResponseDto bakeryCategoryResponseDto, UserInfoResponseDto userResponseDto) {

        this.id = bakery.getId();
        this.title = bakery.getTitle();
        this.roadAddress = bakery.getAddress().getRoadAddress();
        this.mapX = bakery.getPoint().getMapX();
        this.mapY = bakery.getPoint().getMapY();
        this.userId = userResponseDto.getId();
        this.profileImgUrl = userResponseDto.getProfileImgUrl();
        this.breadStyleName = userResponseDto.getBreadStyleName();
        this.breadStyleColor = userResponseDto.getBreadStyleColor();
        this.nickName = userResponseDto.getNickName();
        this.categoryTitle = bakeryCategoryResponseDto.getTitle();
        this.categoryImgUrl = bakeryCategoryResponseDto.getTitleColoredImgUrl();
        this.content = bakeryReview.getContent();
        this.signatureMenus = bakeryReview.getSignatureMenus();

    }
}
