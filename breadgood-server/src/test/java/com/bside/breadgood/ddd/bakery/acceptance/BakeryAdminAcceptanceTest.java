package com.bside.breadgood.ddd.bakery.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryAdminRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryResponseDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryReviewResponseDto;
import com.bside.breadgood.ddd.bakery.application.exception.ReviewNotFoundException;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture;
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

import java.util.List;
import java.util.function.Predicate;

import static com.bside.breadgood.ddd.bakery.acceptance.BakeryAcceptanceTest.*;
import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto.DEFAULT_USER_INFO_RESPONSE_DTO;
import static com.bside.breadgood.fixtures.bakery.BakeryFixture.빵집1_등록요청;
import static com.bside.breadgood.fixtures.bakery.BakeryFixture.빵집2_등록요청;
import static com.bside.breadgood.fixtures.bakeryreview.BakeryReviewFixture.리뷰등록요청;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_200;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * author : haedoang
 * date : 2022/06/09
 * description :
 */
@DisplayName("관리자 빵집 관리 인수테스트")
public class BakeryAdminAcceptanceTest extends AcceptanceTest {
    public static final String BAKERY_ADMIN_BASE_URI = "api/v1/admin/bakery";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermsTypeRepository termsTypeRepository;

    @Autowired
    private BreadStyleRepository breadStyleRepository;

    @Autowired
    private BakeryCategoryRepository bakeryCategoryRepository;

    @Autowired
    private EmojiRepository emojiRepository;

    @Autowired
    private UserService userService;

    String 관리자_토큰;
    String 사용자_토큰;
    User 사용자;
    Long 등록된_빵집_ID;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        빵집_초기_데이터();
    }

    private void 빵집_초기_데이터() {
        final BreadStyle savedBreadStyle = breadStyleRepository.save(달콤_200);
        final TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);
        Long savedEmojiId = emojiRepository.save(이모지_200).getId();
        Long savedBakeryCategoryId = bakeryCategoryRepository.save(BakeryCategoryFixture.빵에집중).getId();

        userRepository.save(
                관리자_등록_요청(
                        관리자.getNickName(),
                        관리자.getEmail(),
                        관리자.getPassword(),
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId()
                ));

        사용자 = userRepository.save(
                사용자_등록_요청(
                        테스트유저.getNickName(),
                        테스트유저.getEmail(),
                        테스트유저.getPassword(),
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId(),
                        Role.USER
                ));

        관리자_토큰 = 로그인_토큰(관리자.getEmail(), 관리자.getPassword());
        사용자_토큰 = 로그인_토큰(테스트유저.getEmail(), 테스트유저.getPassword());
        등록된_빵집_ID = 빵집_등록되어있음(사용자_토큰, 빵집1_등록요청(savedBakeryCategoryId, savedEmojiId));
        빵집_리뷰_등록_요청함(리뷰등록요청(savedEmojiId), 관리자_토큰, 등록된_빵집_ID);
        빵집_등록되어있음(사용자_토큰, 빵집2_등록요청(savedBakeryCategoryId, savedEmojiId));
    }

    @Test
    @DisplayName("관리자는 관리자 빵집 리스트를 조회할 수 있다")
    public void bakeryListOk() {
        // given
        int expectedCount = 2;

        // when
        final ExtractableResponse<Response> response = 빵집_조회_요쳥함(관리자_토큰);

        // then
        빵집_조회됨(response, expectedCount);
    }

    @Test
    @DisplayName("사용자는 관리자 빵집 리스트를 조회할 권한이 없다")
    public void bakeryListNGByAuthority() {
        // when
        final ExtractableResponse<Response> response = 빵집_조회_요쳥함(사용자_토큰);

        // then
        조회_실패함(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("토큰이 없이는 관리자 빵집 리스트를 조회할 수 없다")
    public void bakeryListNGByNOToken() {
        // when
        final ExtractableResponse<Response> response = 빵집_조회_요청함_토큰x();

        // then
        조회_실패함(response, HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("삭제된 사용자가 등록한 빵집은 사용자 정보는 기본값으로 제공한다")
    public void bakeryListWithDeletedUser() {
        // given
        사용자를_삭제한다(사용자);

        // when
        final ExtractableResponse<Response> response = 빵집_조회_요쳥함(관리자_토큰);

        // then
        빵집_조회됨(response, 2);
        삭제된사용자로_정보조회됨(response);
    }

    @Test
    @DisplayName("관리자는 빵집을 삭제할 수 있다")
    public void deleteBakeryOK() {
        // given
        final BakeryAdminRequestDto 등록된_빵집 = 등록된_빵집리스트(관리자_토큰).get(0);

        // when
        final ExtractableResponse<Response> response = 빵집_삭제_요청함(관리자_토큰, 등록된_빵집.getId());

        // then
        빵집_삭제됨(response);
    }

    @Test
    @DisplayName("등록되지 않은 빵집을 삭제 요청 시 예외가 발생한다")
    public void deleteBakeryNGByNoToken() {
        // given
        Long 등록되지않은_빵집ID = Long.MAX_VALUE;
        // when
        final ExtractableResponse<Response> response = 빵집_삭제_요청함(관리자_토큰, 등록되지않은_빵집ID);

        // then
        삭제_실패함(response, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("사용자는 빵집을 삭제할 권한이 없다")
    public void deleteBakeryNGByAutority() {
        // given
        final BakeryAdminRequestDto 등록된_빵집 = 등록된_빵집리스트(관리자_토큰).get(0);

        // when
        final ExtractableResponse<Response> response = 빵집_삭제_요청함(사용자_토큰, 등록된_빵집.getId());

        // then
        삭제_실패함(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("관리자는 리뷰를 삭제할 수 있다")
    public void deleteReviewOK() {
        // given
        final BakeryResponseDto bakeryResponseDto = 빵집_조회_요청함(등록된_빵집_ID, 사용자_토큰);
        final Long 회원_리뷰_ID = getReview(bakeryResponseDto, false, 사용자.getId());

        // when
        final ExtractableResponse<Response> response = 리뷰_삭제_요청함(관리자_토큰, 등록된_빵집_ID, 회원_리뷰_ID);

        // then
        리뷰_삭제됨(response);
    }

    @Test
    @DisplayName("사용자는 리뷰를 삭제할 권한이 없다")
    public void deleteReviewNGByAuthority() {
        // given
        final BakeryResponseDto bakeryResponseDto = 빵집_조회_요청함(등록된_빵집_ID, 사용자_토큰);
        final Long 회원_리뷰_ID = getReview(bakeryResponseDto, false, 사용자.getId());

        // when
        final ExtractableResponse<Response> response = 리뷰_삭제_요청함(사용자_토큰, 등록된_빵집_ID, 회원_리뷰_ID);

        // then
        리뷰_삭제_실패함(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("삭제할 빵집 번호가 유효하지 않은 경우 예외가 발생한다")
    public void deleteReviewNGByUnknownBakery() {
        // given
        final Long NOT_EXIST_BAKERY_ID = Long.MAX_VALUE;
        final BakeryResponseDto bakeryResponseDto = 빵집_조회_요청함(등록된_빵집_ID, 사용자_토큰);
        final Long 회원_리뷰_ID = getReview(bakeryResponseDto, false, 사용자.getId());

        // when
        final ExtractableResponse<Response> response = 리뷰_삭제_요청함(관리자_토큰, NOT_EXIST_BAKERY_ID, 회원_리뷰_ID);

        // then
        리뷰_삭제_실패함(response, HttpStatus.BAD_REQUEST);

    }

    @Test
    @DisplayName("삭제할 빵집의 유효하지 않은 리뷰인 경우 예외가 발생한다")
    public void deleteReviewNGByUnknownReview() {
        // given
        final Long NOT_EXIST_REVIEW_ID = Long.MAX_VALUE;

        // when
        final ExtractableResponse<Response> response = 리뷰_삭제_요청함(관리자_토큰, 등록된_빵집_ID, NOT_EXIST_REVIEW_ID);

        // then
        리뷰_삭제_실패함(response, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("최초 등록자의 리뷰는 삭제할 수 없다")
    public void deleteReviewNGByAuthorsReview() {
        // given
        final BakeryResponseDto bakeryResponseDto = 빵집_조회_요청함(등록된_빵집_ID, 사용자_토큰);
        final Long 최초등록자_리뷰_ID = getReview(bakeryResponseDto, true, 사용자.getId());

        // when
        final ExtractableResponse<Response> response = 리뷰_삭제_요청함(관리자_토큰, 등록된_빵집_ID, 최초등록자_리뷰_ID);

        // then
        리뷰_삭제_실패함(response, HttpStatus.BAD_REQUEST);
    }

    private void 빵집_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 빵집_삭제_요청함(String token, Long id) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when()
                .delete(BAKERY_ADMIN_BASE_URI + "/" + id)
                .then().log().all()
                .extract();
    }

    private void 삭제된사용자로_정보조회됨(ExtractableResponse<Response> response) {
        final List<BakeryAdminRequestDto> actual = response.jsonPath().getList("", BakeryAdminRequestDto.class);
        actual.stream()
                .map(BakeryAdminRequestDto::getUser)
                .forEach(user -> {
                    assertAll(
                            () -> assertThat(user.getId()).isEqualTo(DEFAULT_USER_INFO_RESPONSE_DTO.getId()),
                            () -> assertThat(user.getNickName()).isEqualTo(DEFAULT_USER_INFO_RESPONSE_DTO.getNickName()),
                            () -> assertThat(user.getBreadStyleId()).isEqualTo(DEFAULT_USER_INFO_RESPONSE_DTO.getBreadStyleId()),
                            () -> assertThat(user.getProfileImgUrl()).isEqualTo(DEFAULT_USER_INFO_RESPONSE_DTO.getProfileImgUrl()),
                            () -> assertThat(user.getBreadStyleName()).isEqualTo(DEFAULT_USER_INFO_RESPONSE_DTO.getBreadStyleName())
                    );
                });
    }

    private ExtractableResponse<Response> 빵집_조회_요청함_토큰x() {
        return RestAssured
                .given()
                .log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BAKERY_ADMIN_BASE_URI + "/list")
                .then().log().all()
                .extract();
    }

    private List<BakeryAdminRequestDto> 등록된_빵집리스트(String token) {
        return 빵집_조회_요쳥함(token)
                .jsonPath().getList("", BakeryAdminRequestDto.class);
    }

    private static ExtractableResponse<Response> 빵집_조회_요쳥함(String token) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BAKERY_ADMIN_BASE_URI + "/list")
                .then().log().all()
                .extract();
    }


    private void 빵집_조회됨(ExtractableResponse<Response> response, int expectedCount) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<BakeryAdminRequestDto> actual = response.jsonPath().getList("", BakeryAdminRequestDto.class);
        assertThat(actual).hasSize(expectedCount);
    }

    private void 조회_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private void 삭제_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private void 사용자를_삭제한다(User user) {
        userService.withdrawal(user.getId());
    }


    private void 리뷰_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 리뷰_삭제_요청함(String token, Long bakeryId, Long reviewId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .delete(BAKERY_ADMIN_BASE_URI + "/pages/webview-api/bakery/" + bakeryId + "/review/" + reviewId)
                .then().log().all()
                .extract();
    }

    private void 리뷰_삭제_실패함(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    private Long getReview(BakeryResponseDto response, boolean isOwner, Long userId) {
        Predicate<BakeryReviewResponseDto> isAuthorReview = e -> e.getUserId().equals(userId);
        Predicate<BakeryReviewResponseDto> notAuthorReview = e -> !e.getUserId().equals(userId);

        return response.getBakeryReviews()
                .stream()
                .filter(isOwner ? isAuthorReview : notAuthorReview)
                .findAny()
                .orElseThrow(ReviewNotFoundException::new)
                .getId();
    }
}
