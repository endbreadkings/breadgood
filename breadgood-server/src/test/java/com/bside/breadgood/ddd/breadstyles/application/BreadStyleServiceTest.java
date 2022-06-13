package com.bside.breadgood.ddd.breadstyles.application;

import com.bside.breadgood.ddd.breadstyles.application.exception.BreadStyleNotFoundException;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleRequestDto;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.utils.EntityReflectionUtils;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import com.bside.breadgood.s3.application.S3Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.*;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.담백_400;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.짭짤_300;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.짭짤빵_요청이미지;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.짭짤빵프로필_요청이미지;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.최애빵스타일_등록요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;

@DisplayName("최애빵 스타일 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BreadStyleServiceTest {

    @Mock
    private BreadStyleRepository breadStyleRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private BreadStyleService breadStyleService;

    @Test
    @DisplayName("최애빵 스타일 리스트 조회")
    void findAll() {
        // given
        final List<BreadStyle> expected = Arrays.asList(짭짤_300, 담백_400);
        given(breadStyleRepository.findAllByOrderBySortNumberAsc()).willReturn(expected);
        // when
        List<BreadStyleResponseDto> actual = breadStyleService.findAll();
        // then
        assertThat(actual).containsExactlyElementsOf(
                Arrays.asList(new BreadStyleResponseDto(짭짤_300),
                        new BreadStyleResponseDto(담백_400)
                )
        );
    }

    @Test
    @DisplayName("최애빵 스타일 조회")
    void findById() {
        // given
        BreadStyle expected = 담백_400;
        given(breadStyleRepository.findById(anyLong())).willReturn(Optional.of(expected));
        // when
        final BreadStyleResponseDto actual = breadStyleService.findById(anyLong());
        // then
        assertThat(actual).isEqualTo(new BreadStyleResponseDto(담백_400));
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

    @Test
    @DisplayName("최애빵 스타일 id 리스트로 조회한다")
    public void findAllById() {
        // given
        EntityReflectionUtils.setId(크림_100, BreadStyle.class, 1L);
        EntityReflectionUtils.setId(달콤_200, BreadStyle.class, 2L);
        EntityReflectionUtils.setId(짭짤_300, BreadStyle.class, 3L);
        given(breadStyleRepository.findAllById(anySet())).willReturn(Lists.newArrayList(크림_100, 달콤_200, 짭짤_300));

        // when
        final Map<Long, BreadStyleResponseDto> actual = breadStyleService.findAllById(Sets.newHashSet(1L, 2L, 3L));

        // then
        assertThat(actual).hasSize(3);
        assertThat(actual.values()).extracting(BreadStyleResponseDto::getId).contains(1L, 2L, 3L);
    }
  
    @DisplayName("최애빵 스타일 저장")
    void save() {
        // given
        given(s3Service.upload(짭짤빵_요청이미지, "admin"))
            .willReturn("admin/" + 짭짤빵_요청이미지.getName());
        given(s3Service.upload(짭짤빵프로필_요청이미지, "admin"))
            .willReturn("admin/" + 짭짤빵프로필_요청이미지.getName());
        given(s3Service.getFileHost()).willReturn("https://d74hbwjus7qtu.cloudfront.net/");

        given(breadStyleRepository.save(any())).willReturn(짭짤_300);

        BreadStyleRequestDto 짭짤빵스타일_등록요청 = 최애빵스타일_등록요청("짭짤",
            "피자빵, 고로케,양파빵, \n" +
                "마늘바게트 등 \n" +
                "짭짤한 맛의 조리빵",
            "#FFBC4A");

        //when
        BreadStyleResponseDto actual = breadStyleService.save(짭짤빵스타일_등록요청, 짭짤빵_요청이미지, 짭짤빵프로필_요청이미지);

        //then
        assertThat(actual).isEqualTo(new BreadStyleResponseDto(짭짤_300));
    }
}
