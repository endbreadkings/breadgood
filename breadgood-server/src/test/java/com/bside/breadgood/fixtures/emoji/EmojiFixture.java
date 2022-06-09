package com.bside.breadgood.fixtures.emoji;

import com.bside.breadgood.ddd.emoji.domain.Emoji;

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
                    "img.url",
                    1
            );

    public static final Emoji 이모지2 = Emoji.builder()
            .name("이모지2")
            .imgUrl("https://d74hbwjus7qtu.cloudfront.net/admin/address-icon.png")
            .sortNumber(200)
            .build();
}
