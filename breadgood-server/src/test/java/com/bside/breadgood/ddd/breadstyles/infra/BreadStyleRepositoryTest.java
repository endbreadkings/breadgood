package com.bside.breadgood.ddd.breadstyles.infra;

import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.크림_100;
import static org.assertj.core.api.Assertions.assertThat;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * create on 2022/05/10. create by IntelliJ IDEA.
 *
 * <p> 최애빵 스타일 레파지토리 테스트 </p>
 *
 * @author Yeonha Kim
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("최애빵 스타일 레파지토리 테스트")
class BreadStyleRepositoryTest {
  @Autowired
  BreadStyleRepository repository;

  @BeforeEach
  void init() {
    repository.save(달콤_200);
    repository.save(크림_100);
  }

  @AfterEach
  void destroy() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("최애빵 sort_number 오름차순 조회 테스트")
  void findAllOrderBySortNumberAscTest() {
    // when
    List<BreadStyle> breadStyles = repository.findAllByOrderBySortNumberAsc();

    // then
    assertThat(breadStyles).containsExactly(크림_100, 달콤_200);
  }
}