package com.bside.breadgood.ddd.termstype.infra;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.fixtures.termstype.TermsTypeFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/03/20
 * description :
 */
@DataJpaTest
@DisplayName("약관 리파지토리 테스트")
@ActiveProfiles("test")
public class TermsTypeRepositoryTest {

    @Autowired
    private TermsTypeRepository repository;

    @Test
    @DisplayName("필수 약관 등록 테스트")
    public void addRequiredTermsType() {
        // given
        final TermsType 필수약관 = 필수_위치_기반_서비스_약관_동의;

        // when
        final TermsType termsType = repository.save(필수약관);

        // then
        assertThat(termsType.getId()).isNotNull();
        assertThat(termsType.getTerms()).hasSize(1);
        assertThat(termsType.isRequired()).isTrue();
    }

    @Test
    @DisplayName("선택 약관 등록 테스트")
    public void addNonRequiredTermsType() {
        final TermsType 선택약관 = 선택_광고_이용_정보_동의;

        // when
        final TermsType termsType = repository.save(선택약관);

        // then
        assertThat(termsType.getId()).isNotNull();
        assertThat(termsType.getTerms()).hasSize(1);
        assertThat(termsType.isRequired()).isFalse();
    }

    @Test
    @DisplayName("약관 내용 추가하기")
    public void addTermsContent() {
        // given
        final TermsType savedTermsType = repository.save(필수_개인정보_수집_및_이용_동의_약관_진행중);

        // when
        savedTermsType.addTerms(광고_약관_내용);

        // then
        assertThat(savedTermsType.getTerms()).hasSize(2);
    }
}
