package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.domain.BakeryReview;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.users.application.UserInfoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@ToString
public class BakeryReviewResponseDto {

    @ApiModelProperty(value = "빵집 리뷰 내용", example = "잉 너무 맛있는 걸욧??")
    private final String content;
    @ApiModelProperty(value = "빵집 리뷰 시그니처 메뉴", example = "[\"딸기크림케이크\", \"단팥빵\", \"카야소보로\"]")
    private final List<String> signatureMenus;
    @ApiModelProperty(value = "리뷰 등록 썸네일 이미지들", example = "[\"https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png\"]")
    private final List<String> thumbnailImgUrls;
    @ApiModelProperty(value = "리뷰 등록 상세 이미지들", example = "[\"https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png\"]")
    private final List<String> detailImgUrls;
    @ApiModelProperty(value = "리뷰 등록 시간", example = "2021-07-18 12:37")
    private final String create_at;
    @ApiModelProperty(value = "리뷰를 등록한 회원 아이디", example = "1")
    private final Long userId;
    @ApiModelProperty(value = "리뷰를 등록한 회원 별명", example = "빵긋")
    private final String nickName;
    @ApiModelProperty(value = "리뷰를 등록한 회원 최애빵 스타일 이름", example = "https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png")
    private final String breadStyleName;
    @ApiModelProperty(value = "리뷰를 등록한 회원 최애빵 프로필 이미지", example = "https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png")
    private final String profileImgUrl;
    @ApiModelProperty(value = "리뷰 등록 이모지 이미지", example = "https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png")
    private final String emojiImgUrl;
    @ApiModelProperty(value = "리뷰 등록 이모지 이름", example = "달콤")
    private final String emojiName;

    @Builder
    public BakeryReviewResponseDto(BakeryReview bakeryReview, UserInfoResponseDto userInfoResponseDto, EmojiResponseDto emojiResponseDto) {
        this.content = bakeryReview.getContent();
        this.signatureMenus = bakeryReview.getSignatureMenus();
        this.thumbnailImgUrls = bakeryReview.getImgUrls();
        this.detailImgUrls = bakeryReview.getImgUrls();
        this.create_at = bakeryReview.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
        this.userId = bakeryReview.getUser();
        this.nickName = userInfoResponseDto.getNickName();
        this.breadStyleName = userInfoResponseDto.getBreadStyleName();
        this.profileImgUrl = userInfoResponseDto.getProfileImgUrl();
        this.emojiImgUrl = emojiResponseDto.getImgUrl();
        this.emojiName = emojiResponseDto.getName();
    }
}
