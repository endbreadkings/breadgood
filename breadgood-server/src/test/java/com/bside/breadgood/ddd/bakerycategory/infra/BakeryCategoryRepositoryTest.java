package com.bside.breadgood.ddd.bakerycategory.infra;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.빵에집중;
import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.음료와빵;
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
        bakeryCategoryRepository.saveAll(List.of(음료와빵, 빵에집중));

        // when
        final List<BakeryCategory> actual = bakeryCategoryRepository.findAllOrderBySortNumberAsc();

        // then
        assertThat(actual.get(0).getSortNumber()).isLessThan(actual.get(1).getSortNumber());
    }
}
