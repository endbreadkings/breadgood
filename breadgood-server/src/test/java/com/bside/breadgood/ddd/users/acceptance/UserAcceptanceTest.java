package com.bside.breadgood.ddd.users.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.fixtures.BreadStyleFixture;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.LoginRequest;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.bside.breadgood.ddd.breadstyles.fixtures.BreadStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/02/13
 * description :
 */
@DisplayName("사용자 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {
    public static final String USER_BASE_URI = "api/v1/user";

    @Autowired
    private UserService userService;

    @Autowired
    private BreadStyleService breadStyleService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        breadStyleService.save(달콤);
        breadStyleService.save(짭짤);
        breadStyleService.save(크림);
        breadStyleService.save(담백);

        userService.initData(); //termsType fixtures 구현 후 refactoring 예정
    }

    @Test
    @DisplayName("회원 로그인 시 토큰을 발급받는다")
    void siginInOk() {
        // given
        final LoginRequest 등록된_사용자_로그인_요청 = LoginRequest.valueOf("test@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 로그인_요청함(등록된_사용자_로그인_요청);

        // then
        로그인_성공함(response);
        로그인_토큰_발급됨(response);
    }

    @Test
    @DisplayName("회원 로그인 실패 시 Error 코드를 반환한다")
    public void singInFail() {
        // given
        final LoginRequest 미등록된_사용자_로그인_요청 = LoginRequest.valueOf("notExist@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 로그인_요청함(미등록된_사용자_로그인_요청);

        // then
        로그인_실패함(response);
    }

    private ExtractableResponse<Response> 로그인_요청함(LoginRequest 등록된_사용자_로그인_요청) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(등록된_사용자_로그인_요청)
                .when()
                .post(USER_BASE_URI + "/signin")
                .then().log().all().extract();
    }

    private void 로그인_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private void 로그인_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 로그인_토큰_발급됨(ExtractableResponse<Response> response) {
        final TokenRefreshResponse actual = response.jsonPath().getObject("", TokenRefreshResponse.class);
        assertThat(actual.getTokenType()).isEqualTo("Bearer");
        assertThat(actual.getAccessToken()).isNotNull();
        assertThat(actual.getAccessTokenExpirationTimeMsec()).isGreaterThan(Timestamp.valueOf(LocalDateTime.now()).getTime());
    }
}
