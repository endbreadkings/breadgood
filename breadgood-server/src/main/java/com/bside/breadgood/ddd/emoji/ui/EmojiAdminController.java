package com.bside.breadgood.ddd.emoji.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2022/06/05. create by IntelliJ IDEA.
 *
 * <p> 이모지 관련 어드민 컨트롤러 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@Api(value = "emoji", description = "[관리자]이모지 API's")
@RestController
@RequestMapping("/api/v1/admin/emoji")
@RequiredArgsConstructor
public class EmojiAdminController {

  private final EmojiService emojiService;

  @ApiOperation(value = "관리자에서 이모지 리스트를 조회 합니다.", notes = "")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "성공적으로 조회시 EmojiResponseDto 반환", response = EmojiResponseDto.class, responseContainer = "List"),
      @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
      @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
      @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
  )
  @ApiImplicitParams({
  })
  @GetMapping("/list")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<EmojiResponseDto> findAll() {
    return emojiService.findAll();
  }
}
