package com.bside.breadgood.ddd.bakerycategory.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BakeryCategoryRequestDto {

    private final MultipartFile titleColoredImg;
    private final MultipartFile titleUncoloredImg;
    private final MultipartFile markerImg;
    private final String color;
    private final String content;
    private final String title;

    @Builder
    public BakeryCategoryRequestDto(MultipartFile titleColoredImg, MultipartFile titleUncoloredImg, MultipartFile markerImg, String color, String content, String title) {
        this.titleColoredImg = titleColoredImg;
        this.titleUncoloredImg = titleUncoloredImg;
        this.markerImg = markerImg;
        this.color = color;
        this.content = content;
        this.title = title;
    }
}
