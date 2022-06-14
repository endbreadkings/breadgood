package com.bside.breadgood.ddd.bakery.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryResponseDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryReviewRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;

import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.fixtures.bakery.BakeryFixture.빵집1_등록요청;
import static com.bside.breadgood.fixtures.bakeryreview.BakeryReviewFixture.리뷰등록요청;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_200;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/06/09
 * description :
 */
@DisplayName("빵집 관리 인수테스트")
public class BakeryAcceptanceTest extends AcceptanceTest {
    public static final String BASE_URI = "/api/v1/bakery";

    static String 사용자_토큰;
    static String 관리자_토큰;
    Long 최애빵_스타일_ID;
    Long 이모지_ID;
    Long 빵카테고리_ID;

    @Autowired
    private BreadStyleRepository breadStyleRepository;

    @Autowired
    private BakeryCategoryRepository bakeryCategoryRepository;

    @Autowired
    private TermsTypeRepository termsTypeRepository;

    @Autowired
    private EmojiRepository emojiRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        사용자_초기_데이터();
    }

    private void 사용자_초기_데이터() {
        최애빵_스타일_ID = breadStyleRepository.save(달콤_200).getId();
        이모지_ID = emojiRepository.save(이모지_200).getId();
        빵카테고리_ID = bakeryCategoryRepository.save(BakeryCategoryFixture.빵에집중).getId();
        TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);

        userRepository.save(
                사용자_등록_요청(
                        테스트유저.getNickName(),
                        테스트유저.getEmail(),
                        테스트유저.getPassword(),
                        Lists.newArrayList(savedTermsType),
                        최애빵_스타일_ID,
                        Role.USER
                ));

        userRepository.save(
                관리자_등록_요청(
                        관리자.getNickName(),
                        관리자.getEmail(),
                        관리자.getPassword(),
                        Lists.newArrayList(savedTermsType),
                        최애빵_스타일_ID
                ));

        사용자_토큰 = 로그인_토큰(테스트유저.getEmail(), 테스트유저.getPassword());
        관리자_토큰 = 로그인_토큰(관리자.getEmail(), 관리자.getPassword());
    }

    @Test
    @DisplayName("빵집을 등록한다")
    public void saveBakery() {
        // given
        final BakerySaveRequestDto 빵집등록요청 = 빵집1_등록요청(빵카테고리_ID, 이모지_ID);

        // when
        final ExtractableResponse<Response> response = 빵집_등록_요청함(사용자_토큰, 빵집등록요청);

        // then
        빵집_등록됨(response);
    }

    @Test
    @DisplayName("빵집 리뷰를 등록한다")
    public void addReview() {
        // given
        final Long 등록된_빵집_ID = 빵집_등록되어있음(관리자_토큰, 빵집1_등록요청(빵카테고리_ID, 이모지_ID));

        // when
        final boolean actual = 빵집_리뷰_등록_요청함(리뷰등록요청(이모지_ID), 사용자_토큰, 등록된_빵집_ID);

        // then
        빵집리뷰_등록됨(actual);
    }

    @Test
    @DisplayName("빵집을 조회한다")
    public void findBakery() {
        // given
        final Long 등록된_빵집_ID = 빵집_등록되어있음(관리자_토큰, 빵집1_등록요청(빵카테고리_ID, 이모지_ID));

        // when
        final BakeryResponseDto actual = 빵집_조회_요청함(등록된_빵집_ID, 사용자_토큰);

        // then
        빵집_조회됨(actual);
    }

    private void 빵집_조회됨(BakeryResponseDto actual) {
        assertThat(actual).isNotNull();
    }

    public static BakeryResponseDto 빵집_조회_요청함(Long 등록된_빵집_ID, String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(BASE_URI + "/" + 등록된_빵집_ID)
                .then().log().all()
                .extract()
                .as(BakeryResponseDto.class);
    }

    public static boolean 빵집_리뷰_등록_요청함(BakeryReviewRequestDto request, String token, Long bakeryId) {
        final RequestSpecification requestSpecification = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .when()
                .multiPart(new MultiPartSpecBuilder(request.getContent()).controlName("content").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getEmojiId()).controlName("emojiId").build());

        for (String menu : request.getSignatureMenus()) {
            requestSpecification.multiPart(
                    new MultiPartSpecBuilder(menu).controlName("signatureMenus").charset("UTF-8").build());
        }

        return requestSpecification.when()
                .post(BASE_URI + "/" + bakeryId + "/review")
                .then().log().all().extract()
                .as(Boolean.class);
    }

    private void 빵집리뷰_등록됨(boolean actual) {
        assertThat(actual).isTrue();
    }

    public static Long 빵집_등록되어있음(String token, BakerySaveRequestDto request) {
        return 빵집_등록_요청함(token, request).as(Long.class);
    }

    private void 빵집_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 빵집_등록_요청함(String token, BakerySaveRequestDto request) {
        final RequestSpecification requestSpecification = RestAssured
                .given()
                .log().all()
                .auth().oauth2(token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(new MultiPartSpecBuilder(request.getBakeryCategoryId()).controlName("bakeryCategoryId").build())
                .multiPart(new MultiPartSpecBuilder(request.getCity()).controlName("city").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getContent()).controlName("content").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getDescription()).controlName("description").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getDistrict()).controlName("district").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getEmojiId()).controlName("emojiId").build())
                .multiPart(new MultiPartSpecBuilder(request.getMapX()).controlName("mapX").build())
                .multiPart(new MultiPartSpecBuilder(request.getMapY()).controlName("mapY").build())
                .multiPart(new MultiPartSpecBuilder(request.getRoadAddress()).controlName("roadAddress").charset("UTF-8").build())
                .multiPart(new MultiPartSpecBuilder(request.getTitle()).controlName("title").charset("UTF-8").build())
                .multiPart("files", tempFile());

        for (String menu : request.getSignatureMenus()) {
            requestSpecification.multiPart(
                    new MultiPartSpecBuilder(menu).controlName("signatureMenus").charset("UTF-8").build());
        }

        return requestSpecification.when()
                .post(BASE_URI)
                .then().log().all()
                .extract();
    }

    private static File tempFile() {
        return new File("src/test/resources/images/marker.png");
    }
}
