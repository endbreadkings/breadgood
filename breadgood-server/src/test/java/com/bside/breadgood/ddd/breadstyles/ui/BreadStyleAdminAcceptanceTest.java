package com.bside.breadgood.ddd.breadstyles.ui;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.infra.UserRepository;

import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.짭짤_300;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.크림_100;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.관리자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * create on 2022/05/16. create by IntelliJ IDEA.
 *
 * <p> 최애빵 스타일 인수 테스트 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@DisplayName("어드민 최애빵 스타일 인수 테스트")
@TestInstance(Lifecycle.PER_CLASS)
public class BreadStyleAdminAcceptanceTest extends AcceptanceTest {
  @Autowired
  BreadStyleRepository breadStyleRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TermsTypeRepository termsTypeRepository;

  private static final String BASE_URL = "api/v1/admin/breadstyle";

  @Override
  @BeforeAll
  public void setUp() {
    super.setUp();
    초기_데이터();
  }

  private void 초기_데이터() {
    BreadStyle style1 = breadStyleRepository.save(짭짤_300);
    BreadStyle style2 = breadStyleRepository.save(크림_100);
    TermsType termsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);

    userRepository.save(
        관리자_등록_요청(
            "admin",
            "admin@breadgood.com",
            "admin1234",
            List.of(termsType),
            style1.getId()
        )
    );
  }

  @Test
  @DisplayName("최애빵 리스트 조회를 할 때, 정렬 순서대로 조회가 된다.")
  void findAllTest() {
    // given
    final String 토큰 = 로그인_토큰("admin@breadgood.com", "admin1234");

    final ExtractableResponse<Response> response = 최애빵_리스트_조회_요청(토큰);

    최애빵_리스트_조회_성공(response);
  }

  public static ExtractableResponse<Response> 최애빵_리스트_조회_요청(String token) {
    return RestAssured
        .given().log().all()
        .auth().oauth2(token)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when().get(BASE_URL + "/list")
        .then().log().all()
        .extract();
  }

  public static void 최애빵_리스트_조회_성공(ExtractableResponse<Response> response) {
    final List<BreadStyleResponseDto> breadStyleResponseDtos = response.jsonPath()
        .getList(".", BreadStyleResponseDto.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(breadStyleResponseDtos).hasSize(2);

    final BreadStyleResponseDto dto1 = breadStyleResponseDtos.get(0);
    final BreadStyleResponseDto dto2 = breadStyleResponseDtos.get(1);

    assertThat(dto1.getSortNumber()).isLessThan(dto2.getSortNumber());
  }
}
