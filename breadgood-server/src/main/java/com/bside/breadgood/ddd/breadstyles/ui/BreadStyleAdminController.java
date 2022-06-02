package com.bside.breadgood.ddd.breadstyles.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
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
 * create on 2022/04/20. create by IntelliJ IDEA.
 *
 * <p> 최애빵 관련 어드민 컨트롤러 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/admin/breadstyle")
@RequiredArgsConstructor
public class BreadStyleAdminController {

  private final BreadStyleService breadStyleService;

  @ApiOperation(value = "관리자에서 최애빵 스타일 리스트를 조회 합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "성공적으로 조회시 BreadStyleResponseDto 반환", response = BreadStyleResponseDto.class, responseContainer = "List"),
      @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
      @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
      @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
  )
  @GetMapping("/list")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<BreadStyleResponseDto> findAll() {
    return breadStyleService.findAll();
  }
}
