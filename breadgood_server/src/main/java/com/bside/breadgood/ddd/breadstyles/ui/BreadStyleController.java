package com.bside.breadgood.ddd.breadstyles.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.ActiveTermsResponseDto;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "breadStyle", description = "최애빵 스타일 API's")
@RestController
@RequestMapping("/api/v1/breadstyle")
@RequiredArgsConstructor
public class BreadStyleController {

    private final BreadStyleService breadStyleService;

    @ApiOperation(value = "최애빵 스타일 리스트를 조회 합니다.", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 BreadStyleResponseDto 반환", response = BreadStyleResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/list")
    public List<BreadStyleResponseDto> findAll() {
        return breadStyleService.findAll();
    }
}
