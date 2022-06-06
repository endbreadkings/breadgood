package com.bside.breadgood.fixtures.emoji;

import com.bside.breadgood.ddd.emoji.domain.Emoji;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class EmojiFixture {
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
}
