package com.bside.breadgood.ddd.termstype.application;

import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bside.breadgood.fixtures.termstype.TermsTypeFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * author : haedoang
 * date : 2022/04/26
 * description :
 */
@DisplayName("약관 항목 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class TermsTypeServiceTest {
    @InjectMocks
    private TermsTypeService termsTypeService;

    @Mock
    private TermsTypeRepository termsTypeRepository;

    @Test
    @DisplayName("필수 약관 등록 테스트")
    public void saveRequiredTermsType() {
        // given
        given(termsTypeRepository.save(any())).willReturn(필수_개인정보_수집_및_이용_동의_약관_100);

        // when
        final TermsTypeResponseDto actual = termsTypeService.save(필수_개인정보_수집_및_이용_동의_약관_등록요청);

        // then
        assertThat(actual.getName()).isEqualTo("개인정보 수집 및 이용 동의");
        assertThat(actual.isRequired()).isTrue();
    }

    @Test
    @DisplayName("선택 약관 등록 테스트")
    public void saveNotRequiredTermsType() {
        // given
        given(termsTypeRepository.save(any())).willReturn(선택_광고_이용_정보_동의_300);

        // when
        final TermsTypeResponseDto actual = termsTypeService.save(선택_광고_이용_정보_동의_등록요청);

        // then
        assertThat(actual.getName()).isEqualTo("광고 이용 정보 동의");
        assertThat(actual.isRequired()).isFalse();
    }
}
