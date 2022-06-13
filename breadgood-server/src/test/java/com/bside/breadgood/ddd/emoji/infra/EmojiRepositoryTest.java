package com.bside.breadgood.ddd.emoji.infra;

import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_100;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_200;
import static org.assertj.core.api.Assertions.assertThat;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * create on 2022/06/05. create by IntelliJ IDEA.
 *
 * <p> 이모지 레파지토리 테스트 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("이모지 레파지토리 테스트")
class EmojiRepositoryTest {
  @Autowired
  EmojiRepository repository;

  @BeforeEach
  void init() {
    repository.save(이모지_100);
    repository.save(이모지_200);
  }

  @AfterEach
  void destroy() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("이모지 sort_number 오름차순 조회 테스트")
  void findAllOrderBySortNumberAscTest() {
    // when
    List<Emoji> breadStyles = repository.findAllByOrderBySortNumberAsc();

    // then
    assertThat(breadStyles).extracting("sortNumber").contains(100, 200);
  }

  @Test
  @DisplayName("이모지 sort_number 최대값 조회 테스트")
  void findMaxSortNumberTest() {
    // when
    int maxSortNumber = repository.findMaxSortNumber();

    // then
    assertThat(maxSortNumber).isEqualTo(200);
  }

  @Test
  @DisplayName("등록된 이모지가 없을 때, 이모지 sort_number 최대값 조회 테스트")
  void findMaxSortNumberTestGivenNoData() {
    // given
    repository.deleteAll();

    // when
    int maxSortNumber = repository.findMaxSortNumber();

    // then
    assertThat(maxSortNumber).isZero();
  }
}