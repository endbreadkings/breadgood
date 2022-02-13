package com.bside.breadgood.ddd.bakery.application.exception;

import lombok.Getter;

/**
 * create on 2022/02/13. create by IntelliJ IDEA.
 *
 * <p> 빵집 정렬 타입이 잘못 입력됬을 때 발생하는 Exception </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@Getter
public class IllegalSortTypeException extends RuntimeException {
  private final String[] args;

  public IllegalSortTypeException(String... args) {
    this.args = args;
  }
}
