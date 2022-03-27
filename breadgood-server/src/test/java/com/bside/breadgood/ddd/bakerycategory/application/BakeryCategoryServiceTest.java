package com.bside.breadgood.ddd.bakerycategory.application;

import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryFixture.빵에집중;
import static com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryFixture.음료와빵;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("빵집 카테고리 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BakeryCategoryServiceTest {

  @InjectMocks
  BakeryCategoryService bakeryCategoryService;

  @Mock
  BakeryCategoryRepository bakeryCategoryRepository;


  @DisplayName("카테고리 전체조회")
  @Test
  void findAll() {
    // given
    given(bakeryCategoryRepository.findAllOrderBySortNumberAsc()).willReturn(List.of(음료와빵, 빵에집중));

    // when
    List<BakeryCategoryResponseDto> actual = bakeryCategoryService.findAll();

    // then
    assertAll(
            () -> assertEquals(2, actual.size()),
            () -> assertThat(actual.get(0).getSortNumber()).isLessThan(actual.get(1).getSortNumber())
                    .as("빵집 카테고리를 조회하면 정렬번호 (sortNumber) 가 낮은 순으로 조회된다.")
    );

  }
}
