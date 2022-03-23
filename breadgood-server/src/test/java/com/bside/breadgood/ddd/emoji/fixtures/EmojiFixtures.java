package com.bside.breadgood.ddd.emoji.fixtures;

import com.bside.breadgood.ddd.emoji.domain.Emoji;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class EmojiFixtures {
    public static Emoji 이모지1() {
        return new Emoji(
                1L,
                "emojiName",
                "img.url",
                1
        );
    }
}
