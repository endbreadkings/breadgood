package com.bside.breadgood.ddd.bakerycategory.infra;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("빵집 카테고리 저장소 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BakeryCategoryRepositoryTest {

    @Autowired
    BakeryCategoryRepository bakeryCategoryRepository;

    @DisplayName("빵집 카테고리 리스트를 정렬번호가 낮은 순으로 조회한다.")
    @Test
    void findAllOrderBySortNumberAsc() {
        // given
        final BakeryCategory category1 = BakeryCategory.builder()
                .title("카테고리1")
                .titleColoredImgUrl("http://a/a.svg")
                .titleUncoloredImgUrl("http://a/a.svg")
                .color("1")
                .markerImgUrl("http://a/a.svg")
                .content("1")
                .sortNumber(1)
                .build();

        final BakeryCategory category2 = BakeryCategory.builder()
                .title("카테고리2")
                .titleColoredImgUrl("http://b/b.svg")
                .titleUncoloredImgUrl("http://b/b.svg")
                .color("2")
                .markerImgUrl("http://b/b.svg")
                .content("2")
                .sortNumber(2)
                .build();

        bakeryCategoryRepository.saveAll(List.of(category1, category2));

        // when
        final List<BakeryCategory> actual = bakeryCategoryRepository.findAllOrderBySortNumberAsc();

        // then
        assertThat(actual).extracting(BakeryCategory::getSortNumber).isSorted();
    }
}
