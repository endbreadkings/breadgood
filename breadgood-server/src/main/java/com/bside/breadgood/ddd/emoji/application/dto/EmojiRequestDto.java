package com.bside.breadgood.ddd.emoji.application.dto;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * create on 2022/06/06. create by IntelliJ IDEA.
 *
 * <p> 이모지 등록 요청 DTO </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@Getter
public class EmojiRequestDto {

  @ApiModelProperty(value = "리뷰 이모지 이름", example = "존존맛탱구리")
  @NotBlank(message = "A 'name' field is mandatory.")
  private final String name;

  public EmojiRequestDto(String name) {
    this.name = name;
  }

  public Emoji toEntity(String imgUrl, int sortNumber) {
    return Emoji.builder()
        .name(this.name)
        .imgUrl(imgUrl)
        .sortNumber(sortNumber)
        .build();
  }
}
