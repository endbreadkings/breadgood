package com.bside.breadgood.ddd.breadstyles.ui.dto;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * create on 2022/04/20. create by IntelliJ IDEA.
 *
 * <p> 최애빵 스타일 생성 요청 DTO </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@Getter
public class BreadStyleRequestDto {
  @ApiModelProperty(value = "최애빵 이름", example = "매콤")
  @NotBlank(message = "'name' should not be blank")
  private final String name;

  @ApiModelProperty(value = "최애빵 설명", example = "불닭빵, 마라맛 파이리빵 등 매콤 알싸한 맛의 빵")
  @NotBlank(message = "'content' should not be blank")
  private final String content;

  @ApiModelProperty(value = "최애빵 상징 컬러", example = "#ff0000")
  @NotBlank(message = "'color' should not be blank")
  private final String color;

  @Builder
  public BreadStyleRequestDto(String name, String content, String color) {
    this.name = name;
    this.content = content;
    this.color = color;
  }

  public BreadStyle convertToEntity(String contentImgUrl, String profileImgUrl) {
    return BreadStyle.builder()
        .name(this.name)
        .content(this.content)
        .color(this.color)
        .contentImgUrl(contentImgUrl)
        .profileImgUrl(profileImgUrl)
        .build();
  }
}
