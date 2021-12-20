package com.bside.breadgood.ddd.termstype.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.termstype.ui.dto.ActiveTermsResponseDto;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "terms", description = "약관 조회 API's")
@RestController
@RequestMapping("/api/v1/termsType")
@RequiredArgsConstructor
public class TermsController {

    private final TermsTypeService termsTypeService;


    @ApiOperation(value = "집행중인 약관 리스트를 조회 합니다.", notes = "타입 아이디가 루트 아이디 입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 ActiveTermsResponseDto 반환", response = ActiveTermsResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/list")
    public List<ActiveTermsResponseDto> findAll() {
        return termsTypeService.findActiveList();
    }
}
