package com.bside.breadgood.reflection;

import com.bside.breadgood.ddd.utils.EntityReflectionUtils;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/04/25
 * description :
 */
public class EntityReflectionUtilsTest {

    @Test
    @DisplayName("jav reflection 을 사용하여 pk entity 값을 주입한다")
    public void setIdTest() {
        // given
        final Product product = new Product();

        // when
        EntityReflectionUtils.setId(product, Product.class, 1L);

        // then
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Getter
    class Product {
        private Long id;
    }
}
