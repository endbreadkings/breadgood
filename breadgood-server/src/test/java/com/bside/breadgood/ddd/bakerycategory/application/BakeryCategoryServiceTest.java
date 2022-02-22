package com.bside.breadgood.ddd.bakerycategory.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.bside.breadgood.common.vo.ImageUrl;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BakeryCategoryServiceTest {

  BakeryCategoryService bakeryCategoryService;

  BakeryCategoryRepository bakeryCategoryRepository;

  @BeforeEach
  void setUp() {
    bakeryCategoryRepository = Mockito.mock(BakeryCategoryRepository.class);
    bakeryCategoryService = new BakeryCategoryService(bakeryCategoryRepository);
  }

  @Test
  void 카테고리_전체조회() {
    // when
    List<BakeryCategory> categories = getDummyCategoryList();
    when(bakeryCategoryRepository.findAllOrderById()).thenReturn(categories);

    List<BakeryCategoryResponseDto> responseDtos = bakeryCategoryService.findAll();

    // then
    assertEquals(2, responseDtos.size());
  }

  private List<BakeryCategory> getDummyCategoryList() {
    BakeryCategory category1 = new BakeryCategory(1L,
        "카테고리1",
        "카테고리1 설명입니다.",
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        "#FFFFFF",
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        1);
    BakeryCategory category2 = new BakeryCategory(2L,
        "카테고리2",
        "카테고리2 설명입니다.",
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        "#000000",
        ImageUrl.from("https://test.breadgood.com/path1/img.png"),
        2);

    return List.of(category1, category2);
  }
}