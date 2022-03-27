package com.bside.breadgood.ddd.breadstyles.application;

import com.bside.breadgood.ddd.breadstyles.application.exception.BreadStyleNotFoundException;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.bside.breadgood.ddd.breadstyles.fixtures.BreadStyleFixture.담백;
import static com.bside.breadgood.ddd.breadstyles.fixtures.BreadStyleFixture.짭짤;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("최애빵 스타일 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BreadStyleServiceTest {

    @Mock
    private BreadStyleRepository breadStyleRepository;

    @InjectMocks
    private BreadStyleService breadStyleService;

    @Test
    @DisplayName("최애빵 스타일 리스트 조회")
    void findAll() {
        // given
        final List<BreadStyle> expected = Arrays.asList(담백, 짭짤);
        given(breadStyleRepository.findAllOrderByIdDesc()).willReturn(expected);
        // when
        List<BreadStyleResponseDto> actual = breadStyleService.findAll();
        // then
        assertThat(actual).containsExactlyElementsOf(
                Arrays.asList(new BreadStyleResponseDto(담백),
                        new BreadStyleResponseDto(짭짤)
                )
        );
    }

    @Test
    @DisplayName("최애빵 스타일 조회")
    void findById() {
        // given
        BreadStyle expected = 담백;
        given(breadStyleRepository.findById(anyLong())).willReturn(Optional.of(expected));
        // when
        final BreadStyleResponseDto actual = breadStyleService.findById(anyLong());
        // then
        assertThat(actual).isEqualTo(new BreadStyleResponseDto(담백));
    }

    @Test
    @DisplayName("존재하지 않는 최애빵 스타일 조회시 오류가 발생한다.")
    void findByIdWithNotFound() {
        // given
        given(breadStyleRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        assertThrows(BreadStyleNotFoundException.class, () -> {
            breadStyleService.findById(anyLong());
        });
    }
}
