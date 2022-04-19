package com.bside.breadgood.ddd.users.acceptance;

import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.application.dto.LoginRequest;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_진행중;
import static com.bside.breadgood.fixtures.user.UserFixture.사용자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사용자 관리 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {
    public static final String USER_BASE_URI = "api/v1/user";
    public static User 등록된_사용자;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermsTypeRepository termsTypeRepository;

    @Autowired
    private BreadStyleRepository breadStyleRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        사용자_초기_데이터();
    }

    private void 사용자_초기_데이터() {
        final BreadStyle savedBreadStyle = breadStyleRepository.save(달콤);
        final TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_진행중);

        등록된_사용자 = userRepository.save(
                사용자_등록_요청(
                        "tester",
                        "test@breadgood.com",
                        "1234",
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId(),
                        Role.USER
                ));
    }

    @Test
    @DisplayName("회원 로그인 시 토큰을 발급받는다")
    void signInOk() {
        // given
        final LoginRequest 등록된_사용자_로그인_요청 = LoginRequest.valueOf("test@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 로그인_요청함(등록된_사용자_로그인_요청);

        // then
        로그인_성공함(response);
        로그인_토큰_발급됨(response);
    }

    @Test
    @DisplayName("등록되지 않은 회원 로그인 실패 시 예외를 반환한다")
    public void singInFail() {
        // given
        final LoginRequest 미등록된_사용자_로그인_요청 = LoginRequest.valueOf("notExist@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 로그인_요청함(미등록된_사용자_로그인_요청);

        // then
        로그인_실패함_미존재(response);
    }

    public static ExtractableResponse<Response> 로그인_요청함(LoginRequest loginRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when()
                .post(USER_BASE_URI + "/signin")
                .then().log().all().extract();
    }

    public static String 로그인_토큰(String email, String unencryptedPassword) {
        return 로그인_요청함(
                LoginRequest.valueOf(
                        email,
                        unencryptedPassword
                ))
                .jsonPath()
                .getObject("", TokenRefreshResponse.class)
                .getAccessToken();
    }

    private void 로그인_실패함_미존재(ExtractableResponse<Response> response) {
        final ExceptionResponse actual = response.jsonPath().getObject("", ExceptionResponse.class);
        assertThat(actual.getCode()).isEqualTo(-1301);
        assertThat(actual.getMessage()).contains("유저를 찾을 수 없습니다.");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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
