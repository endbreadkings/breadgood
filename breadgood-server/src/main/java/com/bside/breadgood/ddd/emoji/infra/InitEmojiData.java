package com.bside.breadgood.ddd.emoji.infra;

import com.bside.breadgood.ddd.emoji.domain.Emoji;

import java.util.HashSet;
import java.util.Set;

public class InitEmojiData {


    Set<Emoji> data = new HashSet<>();

    {

        add("별로에요", "https://d74hbwjus7qtu.cloudfront.net/admin/e1.png", 100);
        add("음...?", "https://d74hbwjus7qtu.cloudfront.net/admin/e2.png", 200);
        add("괜찮아요", "https://d74hbwjus7qtu.cloudfront.net/admin/e3.png", 300);
        add("존맛탱", "https://d74hbwjus7qtu.cloudfront.net/admin/e4.png", 400);
        add("인생빵집", "https://d74hbwjus7qtu.cloudfront.net/admin/e5.png", 500);

    }

    public Set<Emoji> get() {
        return data;
    }

    private void add(String name, String imgUrl, int sortNumber) {
        data.add(new Emoji(name, imgUrl, sortNumber));
    }
}
