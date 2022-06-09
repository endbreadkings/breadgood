package com.bside.breadgood.ddd.bakery.acceptance;

import com.bside.breadgood.ddd.AcceptanceTest;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.fixtures.bakery.BakeryFixture;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bside.breadgood.ddd.bakerycategory.ui.BakeryCategoryAcceptanceTest.빵집_카테고리_생성;
import static com.bside.breadgood.ddd.users.acceptance.UserAcceptanceTest.관리자_로그인_토큰;
import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.미등록된_빵에집중;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.필수_개인정보_수집_및_이용_동의_약관_100;
import static com.bside.breadgood.fixtures.user.UserFixture.*;
import static com.bside.breadgood.fixtures.user.UserFixture.관리자;

/**
 * author : haedoang
 * date : 2022/06/09
 * description :
 */
@DisplayName("관리자 빵집 관리 인수테스트")
public class BakeryAdminAcceptanceTest extends AcceptanceTest {
    public static final String BAKERY_ADMIN_BASE_URI = "/api/v1/admin/bakery";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermsTypeRepository termsTypeRepository;

    @Autowired
    private BreadStyleRepository breadStyleRepository;

    String 관리자_토큰;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        빵집_초기_데이터();
    }

    private void 빵집_초기_데이터()  {
        try {
            final BreadStyle savedBreadStyle = breadStyleRepository.save(달콤_200);
            final TermsType savedTermsType = termsTypeRepository.save(필수_개인정보_수집_및_이용_동의_약관_100);
            final Long savedBakeryCategoryId = 빵집_카테고리_생성(관리자_토큰, 미등록된_빵에집중).getId();

            userRepository.save(
                    관리자_등록_요청(
                            관리자.getNickName(),
                            관리자.getEmail(),
                            관리자.getPassword(),
                            Lists.newArrayList(savedTermsType),
                            savedBreadStyle.getId()
                    ));

            관리자_토큰 = 관리자_로그인_토큰(관리자.getEmail(), 관리자.getPassword());



        } catch (Exception ignored) {
        }
    }

    @Test
    @DisplayName("관리자 계정으로 빵집 리스트를 조회한다")
    public void bakeryListByAdmin() {
        // given

        // when

        // then
    }
}
