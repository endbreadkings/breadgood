package com.bside.breadgood.ddd.termstype.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.termstype.ui.dto.ActiveTermsResponseDto;
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

import java.util.List;

import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.집행중인약관1;
import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.로그인_토큰;
import static com.bside.breadgood.fixtures.user.UserFixture.사용자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/03/20
 * description :
 */
@DisplayName("약관 인수테스트")
public class TermsTypeAcceptanceTest extends AcceptanceTest {
    public static final String TERMS_TYPE_BASE_URI = "api/v1/termsType";

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

        final TermsType savedTermsType = termsTypeRepository.save(집행중인약관1);

        userRepository.save(
                사용자_등록_요청(
                        "tester",
                        "test@breadgood.com",
                        "1234",
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId()
                ));
    }

    @Test
    @DisplayName("진행중인 약관 조회를 요청할 수 있다")
    public void getTermsList() {
        final String 토큰 = 로그인_토큰("test@breadgood.com", "1234");

        final ExtractableResponse<Response> response = 집행중인_약관_조회_요청함(토큰);

        집행중인_약관_조회됨(response);

    }

    private ExtractableResponse<Response> 집행중인_약관_조회_요청함(String 토큰) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(토큰)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(TERMS_TYPE_BASE_URI + "/list")
                .then().log().all()
                .extract();
    }

    private void 집행중인_약관_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<ActiveTermsResponseDto> activeTermsResponseList = response
                .jsonPath()
                .getList(".", ActiveTermsResponseDto.class);
        assertThat(activeTermsResponseList).hasSize(1);
    }

}
