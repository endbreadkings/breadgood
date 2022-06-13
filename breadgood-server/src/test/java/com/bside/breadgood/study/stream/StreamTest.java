package com.bside.breadgood.study.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/06/14
 * description :
 */
public class StreamTest {
    @Test
    @DisplayName("5의 배수이면서 짝수만 필터한다")
    public void multipleFilterTest() {
        // given
        final List<Integer> numbers = IntStream.rangeClosed(1, 50)
                .boxed()
                .collect(Collectors.toList());
        Predicate<Integer> isEven = e -> e % 2 == 0;
        Predicate<Integer> isMultipleOf5 = e -> e % 5 == 0;

        // when
        final List<Integer> actual = numbers.stream()
                .filter(isEven.and(isMultipleOf5))
                .collect(Collectors.toList());

        // then
        assertThat(actual).containsExactly(10, 20, 30, 40, 50);
    }
}
