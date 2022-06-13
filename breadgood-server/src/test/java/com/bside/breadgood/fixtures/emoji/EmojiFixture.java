package com.bside.breadgood.fixtures.emoji;

import com.bside.breadgood.common.vo.ImageUrl;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiRequestDto;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class EmojiFixture {
    public static final Emoji 이모지1 =
            new Emoji(
                    1L,
                    "emojiName",
                    ImageUrl.from("img.url"),
                    1
            );

    public static final Emoji 이모지2 = Emoji.builder()
            .name("이모지2")
            .imgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png")
            .sortNumber(200)
            .build();

    public static final Emoji 이모지_100 =
            new Emoji(
                    "별로에요",
                    "https://d74hbwjus7qtu.cloudfront.net/admin/e1.png",
                    100
            );

    public static final Emoji 이모지_200 =
            new Emoji(
                    "음...?",
                    "https://d74hbwjus7qtu.cloudfront.net/admin/e2.png",
                    200
            );

    public static final Emoji 이모지_300 =
            new Emoji(
                    "괜찮아요",
                    "https://d74hbwjus7qtu.cloudfront.net/admin/e3.png",
                    300
            );

    public static EmojiRequestDto 이모지_등록요청_생성(String name) {
        return new EmojiRequestDto(name);
    }

    public static MultipartFile 이모지_요청이미지;

    static {
        try {
            이모지_요청이미지 = new MockMultipartFile(
                    "e1.png", "emoji.png",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    new FileInputStream("src/test/resources/images/emoji.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
