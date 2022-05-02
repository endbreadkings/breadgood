package com.bside.breadgood.ddd.termstype.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.termstype.ui.dto.ActiveTermsResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsSaveRequestDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeSaveRequestDto;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.infra.UserRepository;
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

import java.time.LocalDate;
import java.util.List;

import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.*;
import static com.bside.breadgood.fixtures.user.UserFixture.사용자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/04/29
 * description :
 */
@DisplayName("약관 항목 관리자 인수테스트")
public class AdminTermsTypeAcceptanceTest extends AcceptanceTest {
    public static final String ADMIN_TERMS_TYPE_BASE_URI = "api/v1/admin/termsType";
    public static final String ADMIN_TERMS_BASE_URI = "api/v1/admin/terms";

    @Autowired
    TermsTypeRepository termsTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BreadStyleRepository breadStyleRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        사용자_초기_데이터();
    }

    private void 사용자_초기_데이터() {
        final BreadStyle savedBreadStyle = breadStyleRepository.save(달콤);

        final TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);

        userRepository.save(
                사용자_등록_요청(
                        "tester",
                        "test@breadgood.com",
                        "1234",
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId(),
                        Role.USER
                )
        );

        userRepository.save(
                사용자_등록_요청(
                        "admin",
                        "admin@breadgood.com",
                        "1234",
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId(),
                        Role.ADMIN
                )
        );
    }

    @Test
    @DisplayName("사용자는 약관을 등록할 권한이 없다")
    public void saveTermsTypeFailByRole() {
        // given
        final String 사용자토큰 = 로그인_토큰("test@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> 필수약관_등록요청함 = 약관_등록_요청함(사용자토큰, 필수_개인정보_수집_및_이용_동의_약관_등록요청);

        // then
        약관등록_실패함(필수약관_등록요청함, HttpStatus.FORBIDDEN);

        // when
        final ExtractableResponse<Response> 선택약관_등록요청함 = 약관_등록_요청함(사용자토큰, 선택_광고_이용_정보_동의_등록요청);

        // then
        약관등록_실패함(선택약관_등록요청함, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("관리자는 약관을 등록할 수 있다")
    public void saveTermsType() {
        // given
        final String 관리자토큰 = 로그인_토큰("admin@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> 필수약관_등록요청함 = 약관_등록_요청함(관리자토큰, 필수_개인정보_수집_및_이용_동의_약관_등록요청);

        // then
        약관등록_성공함(필수약관_등록요청함, "개인정보 수집 및 이용 동의", true);

        // when
        final ExtractableResponse<Response> 선택약관_등록요청함 = 약관_등록_요청함(관리자토큰, 선택_광고_이용_정보_동의_등록요청);

        // then
        약관등록_성공함(선택약관_등록요청함, "광고 이용 정보 동의", false);
    }

    @Test
    @DisplayName("약관 등록 요청 시 토큰이 없는 경우 예외를 반환한다")
    public void saveTermsTypeExceptToken() {
        // given
        final ExtractableResponse<Response> 필수약관_등록요청함 = 약관_등록_요청함_토큰x(필수_개인정보_수집_및_이용_동의_약관_등록요청);

        // then
        약관등록_실패함(필수약관_등록요청함, HttpStatus.UNAUTHORIZED);

        // when
        final ExtractableResponse<Response> 선택약관_등록요청함 = 약관_등록_요청함_토큰x(선택_광고_이용_정보_동의_등록요청);

        // then
        약관등록_실패함(선택약관_등록요청함, HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("관리자는 진행중인 약관 조회할 수 있다")
    public void getTermsList() {
        // given
        final String 토큰 = 로그인_토큰("admin@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 집행중인_약관_조회_요청함(토큰);

        // then
        집행중인_약관_조회됨(response);
    }

    @Test
    @DisplayName("사용자는 진행중인 약관 조회할 수 없다")
    public void getTermsListFailByUser() {
        // given
        final String 토큰 = 로그인_토큰("test@breadgood.com", "1234");

        // when
        final ExtractableResponse<Response> response = 집행중인_약관_조회_요청함(토큰);

        // then
        약관조회_실패함(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("관리자는 약관을 약관이 등록되어 있다면 내용을 추가할 수 있다")
    public void addTermWhenTermTypeExistByAdmin() {
        // given
        final String 토큰 = 로그인_토큰("admin@breadgood.com", "1234");
        final Long 등록된_필수_약관_ID = 약관_등록되어있음(토큰, 필수_개인정보_수집_및_이용_동의_약관_등록요청);
        final Long 등록된_선택_약관_ID = 약관_등록되어있음(토큰, 선택_광고_이용_정보_동의_등록요청);
        final TermsSaveRequestDto 필수약관_내용_추가_요청 = TermsSaveRequestDto.valueOf(등록된_필수_약관_ID, "변경될 약관 내용입니다1", LocalDate.now());
        final TermsSaveRequestDto 선택약관_내용_추가_요청 = TermsSaveRequestDto.valueOf(등록된_선택_약관_ID, "변경될 약관 내용입니다2", LocalDate.now());

        // when
        final ExtractableResponse<Response> 약관추가_응답1 = 약관_추가_요청함(토큰, 필수약관_내용_추가_요청);

        // then
        약관_추가됨(약관추가_응답1);

        // when
        final ExtractableResponse<Response> 약관추가_응답2 = 약관_추가_요청함(토큰, 선택약관_내용_추가_요청);

        // then
        약관_추가됨(약관추가_응답2);
    }

    @Test
    @DisplayName("사용자는 약관 내용을 추가할 수 없다")
    public void addTermWhenTermTypeExistByUser() {
        // given
        final String 토큰 = 로그인_토큰("test@breadgood.com", "1234");
        final Long 등록된_필수_약관_ID = 약관_등록되어있음(토큰, 필수_개인정보_수집_및_이용_동의_약관_등록요청);
        final TermsSaveRequestDto 필수약관_내용_추가_요청 = TermsSaveRequestDto.valueOf(등록된_필수_약관_ID, "변경될 약관 내용입니다1", LocalDate.now());


        // when
        final ExtractableResponse<Response> response = 약관_추가_요청함(토큰, 필수약관_내용_추가_요청);

        // then
        약관_추가_실패함(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("약관이 존재하지 않는 경우 예외가 발생해야 한다")
    public void addTermFailByNotExistTermsType() {
        // given
        final String 토큰 = 로그인_토큰("admin@breadgood.com", "1234");
        final Long 존재하지않는_약관번호 = Long.MAX_VALUE;
        final TermsSaveRequestDto 필수약관_내용_추가_요청 = TermsSaveRequestDto.valueOf(존재하지않는_약관번호, "변경될 약관 내용입니다1", LocalDate.now());

        // when
        final ExtractableResponse<Response> response = 약관_추가_요청함(토큰, 필수약관_내용_추가_요청);

        // then
        약관_추가_실패함(response, HttpStatus.BAD_REQUEST);
    }

    private void 약관_추가_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private ExtractableResponse<Response> 약관_추가_요청함(String 토큰, TermsSaveRequestDto 필수약관_내용_추가_요청) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(필수약관_내용_추가_요청)
                .when().post(ADMIN_TERMS_BASE_URI)
                .then().log().all()
                .extract();
    }

    private void 약관_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 약관_등록_요청함_토큰x(TermsTypeSaveRequestDto request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ADMIN_TERMS_TYPE_BASE_URI)
                .then().log().all()
                .extract();
    }

    public static Long 약관_등록되어있음(String token, TermsTypeSaveRequestDto request) {
        return 약관_등록_요청함(token, request)
                .jsonPath().getObject("", TermsTypeResponseDto.class)
                .getId();
    }

    private static ExtractableResponse<Response> 약관_등록_요청함(String 토큰, TermsTypeSaveRequestDto request) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ADMIN_TERMS_TYPE_BASE_URI)
                .then().log().all()
                .extract();
    }

    private void 약관등록_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private void 약관조회_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private void 약관등록_성공함(ExtractableResponse<Response> response, String expectedTermsTypeName, boolean expectedTermsTypeRequired) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        final TermsTypeResponseDto responseDto = response.jsonPath().getObject("", TermsTypeResponseDto.class);
        assertThat(responseDto.getName()).isEqualTo(expectedTermsTypeName);
        assertThat(responseDto.isRequired()).isEqualTo(expectedTermsTypeRequired);
    }

    public static ExtractableResponse<Response> 집행중인_약관_조회_요청함(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ADMIN_TERMS_TYPE_BASE_URI + "/list")
                .then().log().all()
                .extract();
    }

    public static void 집행중인_약관_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<ActiveTermsResponseDto> activeTermsResponseList = response
                .jsonPath()
                .getList(".", ActiveTermsResponseDto.class);
        assertThat(activeTermsResponseList).hasSize(1);
    }
}
