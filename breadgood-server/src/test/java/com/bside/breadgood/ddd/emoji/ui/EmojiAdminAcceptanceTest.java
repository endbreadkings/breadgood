package com.bside.breadgood.ddd.emoji.ui;

import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.짭짤_300;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_100;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_200;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.관리자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * create on 2022/06/06. create by IntelliJ IDEA.
 *
 * <p> 관리자 이모지 인수 테스트 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@DisplayName("관리자 이모지 인수 테스트")
@TestInstance(Lifecycle.PER_CLASS)
public class EmojiAdminAcceptanceTest extends AcceptanceTest {
  @Autowired
  EmojiRepository emojiRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TermsTypeRepository termsTypeRepository;

  @Autowired
  BreadStyleRepository breadStyleRepository;

  private static final String BASE_URL = "api/v1/admin/emoji";

  private String 토큰;

  @Override
  @BeforeAll
  public void setUp() {
    super.setUp();

    관리자_등록();
    토큰 = 로그인_토큰("admin@breadgood.com", "admin1234");
  }

  @AfterEach
  public void destroyEmojis() {
    emojiRepository.deleteAll();
  }

  private void 관리자_등록() {
    TermsType termsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);
    BreadStyle style = breadStyleRepository.save(짭짤_300);

    userRepository.save(
        관리자_등록_요청(
            "admin",
            "admin@breadgood.com",
            "admin1234",
            List.of(termsType),
            style.getId()
        )
    );
  }

  @Test
  @DisplayName("이모지 리스트를 조회할 때, 정렬 순서대로 조회가 된다.")
  void findAllTest() {
    // given
    이모지_생성(이모지_200, 이모지_100);

    // when
    final ExtractableResponse<Response> 이모지_리스트_조회_응답 = 이모지_리스트_조회_요청(토큰);

    // then
    이모지_리스트_조회_성공(이모지_리스트_조회_응답);
  }

  public static ExtractableResponse<Response> 이모지_리스트_조회_요청(String token) {
    return RestAssured
        .given().log().all()
        .auth().oauth2(token)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when().get(BASE_URL + "/list")
        .then().log().all()
        .extract();
  }

  public static void 이모지_리스트_조회_성공(ExtractableResponse<Response> response) {
    final List<EmojiResponseDto> emojiResponseDtos = response.jsonPath()
        .getList(".", EmojiResponseDto.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(emojiResponseDtos).hasSize(2);
    assertThat(emojiResponseDtos).extracting("sortNumber").isSorted();
  }

  private void 이모지_생성(Emoji... emojis) {
    emojiRepository.saveAll(Arrays.asList(emojis));
  }
}
