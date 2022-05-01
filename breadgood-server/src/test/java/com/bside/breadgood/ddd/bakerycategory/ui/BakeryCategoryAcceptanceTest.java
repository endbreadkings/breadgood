package com.bside.breadgood.ddd.bakerycategory.ui;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryRequestDto;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.관리자_로그인_토큰;
import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.미등록된_빵에집중;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_진행중;
import static com.bside.breadgood.fixtures.user.UserFixture.관리자_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayName("빵집 카테고리 인수 테스트")
public class BakeryCategoryAcceptanceTest extends AcceptanceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TermsTypeRepository termsTypeRepository;
    @Autowired
    BreadStyleRepository breadStyleRepository;
    String 관리자_토큰;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        사용자_초기_데이터();
    }

    private void 사용자_초기_데이터() {
        final BreadStyle savedBreadStyle = breadStyleRepository.save(달콤);
        final TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_진행중);

        final String 관리자_이메일 = "admin@breadgood.com";
        final String 관리자_비밀번호 = "1234";
        userRepository.save(
                관리자_등록_요청(
                        "관리자",
                        관리자_이메일,
                        관리자_비밀번호,
                        Lists.newArrayList(savedTermsType),
                        savedBreadStyle.getId()
                ));

        관리자_토큰 = 관리자_로그인_토큰(관리자_이메일, 관리자_비밀번호);
    }

    @Test
    void 빵집_카테고리를_관리한다() throws IOException {

        final BakeryCategoryResponseDto 빵에집중 = 빵집_카테고리_생성(관리자_토큰, 미등록된_빵에집중);

        빵집_카테고리_생성_검증(빵에집중);

        final List<BakeryCategoryResponseDto> 조회된_빵집_카테고리 = 빵집_카테고리_조회(관리자_토큰);

        빵집_카테고리_조회_검증(빵에집중, 조회된_빵집_카테고리);
    }

    private void 빵집_카테고리_생성_검증(BakeryCategoryResponseDto bakeryCategoryResponseDto) {
        assertNotNull(bakeryCategoryResponseDto);
    }

    private void 빵집_카테고리_조회_검증(BakeryCategoryResponseDto 빵에집중, List<BakeryCategoryResponseDto> 조회된_빵집_카테고리) {
        assertThat(조회된_빵집_카테고리).contains(빵에집중);
    }

    public static BakeryCategoryResponseDto 빵집_카테고리_생성(String accessToken, BakeryCategoryRequestDto request) throws IOException {

        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(getMultiPartSpecification("titleColoredImg", request.getMarkerImg()))
                .multiPart(getMultiPartSpecification("titleUncoloredImg", request.getMarkerImg()))
                .multiPart(getMultiPartSpecification("markerImg", request.getMarkerImg()))
                .param("color", request.getColor())
                .param("content", request.getContent())
                .param("title", request.getTitle())
                .when()
                .post("/api/v1/admin/bakeryCategory")
                .then().log().all().extract()
                .as(BakeryCategoryResponseDto.class);
    }

    private static MultiPartSpecification getMultiPartSpecification(String name, MultipartFile multipartFile) throws IOException {
        return new MultiPartSpecBuilder(multipartFile.getBytes()).
                fileName(multipartFile.getOriginalFilename()).
                controlName(name).
                mimeType(multipartFile.getContentType()).
                build();
    }

    public static List<BakeryCategoryResponseDto> 빵집_카테고리_조회(String accessToken) {

        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/admin/bakeryCategory/list")
                .then().log().all().extract()
                .jsonPath().getList(".", BakeryCategoryResponseDto.class);
    }


}
