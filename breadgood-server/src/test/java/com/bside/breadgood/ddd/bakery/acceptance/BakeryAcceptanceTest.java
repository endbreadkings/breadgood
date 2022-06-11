package com.bside.breadgood.ddd.bakery.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.bakery.application.BakeryService;
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
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지2;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.사용자_등록_요청;
import static com.bside.breadgood.fixtures.user.UserFixture.테스트유저;
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
    Long savedBreadStyleId;
    Long savedEmojiId;
    Long savedBakeryCategoryId;

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

    @Autowired
    private BakeryService bakeryService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        사용자_초기_데이터();
    }

    private void 사용자_초기_데이터() {
        savedBreadStyleId = breadStyleRepository.save(달콤_200).getId();
        savedEmojiId = emojiRepository.save(이모지2).getId();
        savedBakeryCategoryId = bakeryCategoryRepository.save(BakeryCategoryFixture.빵에집중).getId();

        TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);
        userRepository.save(
                사용자_등록_요청(
                        테스트유저.getNickName(),
                        테스트유저.getEmail(),
                        테스트유저.getPassword(),
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyleId,
                        Role.USER
                ));

        사용자_토큰 = 로그인_토큰(테스트유저.getEmail(), 테스트유저.getPassword());

    }

    @Test
    @DisplayName("빵집을 등록한다")
    public void saveBakery() {
        // given
        final BakerySaveRequestDto 빵집등록요청 = 빵집1_등록요청(savedBakeryCategoryId, savedEmojiId);

        // when
        final ExtractableResponse<Response> response = 빵집_등록_요청함(빵집등록요청);

        // then
        빵집_등록됨(response);
    }

    public static Long 빵집_등록되어있음(BakerySaveRequestDto request) {
        return 빵집_등록_요청함(request).as(Long.class);
    }


    private void 빵집_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


    public static ExtractableResponse<Response> 빵집_등록_요청함(BakerySaveRequestDto request) {
        final RequestSpecification requestSpecification = RestAssured
                .given()
                .log().all()
                .auth().oauth2(사용자_토큰)
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
                .then().log().all().extract();
    }

    private static File tempFile() {
        return new File("src/test/resources/images/marker.png");
    }
}
