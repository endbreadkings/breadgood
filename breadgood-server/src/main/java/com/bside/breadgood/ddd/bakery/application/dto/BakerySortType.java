package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.application.exception.IllegalSortTypeException;
import org.springframework.util.StringUtils;

/**
 * create on 2022/02/06. create by IntelliJ IDEA.
 *
 * <p> 빵집 정렬 타입 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
public enum BakerySortType {
  ID_DESC;

  public static BakerySortType getEnumByName(String name) {
    if (StringUtils.isEmpty(name)) return BakerySortType.ID_DESC;

    for(BakerySortType type : BakerySortType.values()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    throw new IllegalSortTypeException(name);
  }
}
