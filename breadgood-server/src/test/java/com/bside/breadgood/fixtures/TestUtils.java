package com.bside.breadgood.fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * author : haedoang
 * date : 2022/04/14
 * description :
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    /**
     * PK 주입 필요한 경우 사용
     *
     * @param t
     * @param clazz
     * @param <T>
     */
    public static <T> void setId(T t, Class<T> clazz) {
        final Field idField = ReflectionUtils.findField(clazz, "id");
        assert idField != null;
        idField.setAccessible(true);
        ReflectionUtils.setField(idField, t, 1L);
    }
}
