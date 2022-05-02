package com.bside.breadgood.ddd.utils;

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
public class EntityReflectionUtils {
    private static final String ID_FIELD = "id";

    public static <T> void setId(T instance, Class<T> typeToken, Long id) {
        final Field idField = ReflectionUtils.findField(typeToken, ID_FIELD);
        assert idField != null;
        idField.setAccessible(true);
        ReflectionUtils.setField(idField, instance, id);
    }
}
