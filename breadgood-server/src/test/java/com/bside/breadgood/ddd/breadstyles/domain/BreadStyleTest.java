package com.bside.breadgood.ddd.breadstyles.domain;

import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BreadStyleTest {

    @Autowired
    BreadStyleRepository repository;

    @Test
    @DisplayName("최애빵 스타일 등록")
    void save() {

        final String content = "식빵, 바게트, 치아바타, \n" +
                "크루아상, 베이글 등\n" +
                "자극적이지 않은 담백한 빵";
        final String name = "담백";
        final String color = "#8FBCFF";
        final String url = "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png";
        final String profileImgUrl = "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png";
        final BreadStyle breadStyle = BreadStyle.builder()
                .content(content)
                .name(name)
                .color(color)
                .contentImgUrl(url)
                .profileImgUrl(profileImgUrl)
                .build();

        final BreadStyle savedBreadStyle = repository.save(breadStyle);

        assertAll(
                () -> assertThat(savedBreadStyle.getId()).isNotNull(),
                () -> assertThat(savedBreadStyle.getName()).isEqualTo(name),
                () -> assertThat(savedBreadStyle.getContent()).isEqualTo(content),
                () -> assertThat(savedBreadStyle.getContentImgUrl()).isEqualTo(url),
                () -> assertThat(savedBreadStyle.getProfileImgUrl()).isEqualTo(profileImgUrl)
        );

    }

    @ParameterizedTest(name = " \"{0}\" 이름이 빈값일 경우 오류가 발생한다.")
    @NullAndEmptySource
    void saveWithEmptyName(String name) {

        final String content = "식빵, 바게트, 치아바타, \n" +
                "크루아상, 베이글 등\n" +
                "자극적이지 않은 담백한 빵";
        final String color = "#8FBCFF";
        final String url = "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png";
        final String profileImgUrl = "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png";


        assertThrowsBuildBreadStyle(content, name, color, url, profileImgUrl);
    }

    @ParameterizedTest(name = " \"{0}\" 설명이 빈값일 경우 오류가 발생한다.")
    @NullAndEmptySource
    void saveWithEmptyContent(String content) {
        final String name = "담백";
        final String color = "#8FBCFF";
        final String url = "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png";
        final String profileImgUrl = "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png";

        assertThrowsBuildBreadStyle(content, name, color, url, profileImgUrl);
    }

    @ParameterizedTest(name = " \"{0}\" 색상값이 빈 경우 오류가 발생한다.")
    @NullAndEmptySource
    void saveWithEmptyColor(String color) {

        final String content = "식빵, 바게트, 치아바타, \n" +
                "크루아상, 베이글 등\n" +
                "자극적이지 않은 담백한 빵";
        final String name = "담백";
        final String url = "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png";
        final String profileImgUrl = "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png";


        assertThrowsBuildBreadStyle(content, name, color, url, profileImgUrl);
    }

    @ParameterizedTest(name = " \"{0}\" 설명 이미지가 빈값일 경우 오류가 발생한다.")
    @NullAndEmptySource
    void saveWithEmptyContentUrl(String url) {

        final String content = "식빵, 바게트, 치아바타, \n" +
                "크루아상, 베이글 등\n" +
                "자극적이지 않은 담백한 빵";
        final String name = "담백";
        final String color = "#8FBCFF";
        final String profileImgUrl = "https://d74hbwjus7qtu.cloudfront.net/admin/case_2_plain_.png";

        assertThrowsBuildBreadStyle(content, name, color, url, profileImgUrl);
    }


    @ParameterizedTest(name = " \"{0}\" 최애빵 프로필 사진이 빈 경우 오류가 발생한다.")
    @NullAndEmptySource
    void saveWithEmptyProfileImgUrl(String profileImgUrl) {

        final String content = "식빵, 바게트, 치아바타, \n" +
                "크루아상, 베이글 등\n" +
                "자극적이지 않은 담백한 빵";
        final String name = "담백";
        final String color = "#8FBCFF";
        final String url = "https://d74hbwjus7qtu.cloudfront.net/admin/case_1_plain.png";


        assertThrowsBuildBreadStyle(content, name, color, url, profileImgUrl);
    }


    private void assertThrowsBuildBreadStyle(String content, String name, String color, String url, String profileImgUrl) {
        assertThrows(RuntimeException.class, () -> {
            BreadStyle.builder()
                    .content(content)
                    .name(name)
                    .color(color)
                    .contentImgUrl(url)
                    .profileImgUrl(profileImgUrl)
                    .build();
        });
    }
}
