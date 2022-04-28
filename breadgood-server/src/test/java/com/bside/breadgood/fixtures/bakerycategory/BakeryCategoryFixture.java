package com.bside.breadgood.fixtures.bakerycategory;

import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryRequestDto;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BakeryCategoryFixture {

    public static BakeryCategory 음료와빵 = BakeryCategory.builder()
            .title("음료&빵")
            .titleColoredImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/cate1_blue.svg")
            .titleUncoloredImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/cate1_white.svg")
            .color("#4579FF")
            .markerImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/pin_on.png")
            .content("커피&차와 함께 빵을\n 즐길수 있는 베이커리 카페")
            .sortNumber(1)
            .build();

    public static BakeryCategory 빵에집중 = BakeryCategory.builder()
            .title("빵에집중")
            .titleColoredImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/cate2_yellow.svg")
            .titleUncoloredImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/cate2_white.svg")
            .color("#FEBE52")
            .markerImgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/pin_be_off.png")
            .content("빵을 전문적으로 파는\n 일반 베이커리")
            .sortNumber(2)
            .build();

    private static MockMultipartFile markerImg;
    private static MockMultipartFile titleColoredImg;
    private static MockMultipartFile titleUncoloredImg;

    static {
        try {
            markerImg = new MockMultipartFile("markerImg", "marker.png",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    new FileInputStream("src/test/resources/images/marker.png"));

            titleColoredImg = new MockMultipartFile("titleColoredImg", "titleColored.svg",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    new FileInputStream("src/test/resources/images/titleColored.svg"));

            titleUncoloredImg = new MockMultipartFile("titleUncoloredImg", "titleUncolored.svg",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    new FileInputStream("src/test/resources/images/titleUncolored.svg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BakeryCategoryRequestDto 미등록된_빵에집중 = BakeryCategoryRequestDto.builder()
            .color("#FEBE52")
            .content("빵을 전문적으로 파는\n 일반 베이커리")
            .title("빵에집중")
            .markerImg(markerImg)
            .titleColoredImg(titleColoredImg)
            .titleUncoloredImg(titleUncoloredImg)
            .build();

}
