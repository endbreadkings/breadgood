package com.bside.breadgood.ddd.bakerycategory.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "bakeryCategory", description = "빵집 카테고리 API's")
@RestController
@RequestMapping("/api/v1/bakeryCategory")
@RequiredArgsConstructor
public class BakeryCategoryController {

    private final BakeryCategoryService bakeryCategoryService;

    @ApiOperation(value = "빵집 카테고리 리스트를 조회 합니다.", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 BakeryCategoryResponseDto 반환", response = BakeryCategoryResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/list")
    public List<BakeryCategoryResponseDto> findAll() {
        return bakeryCategoryService.findAll();
    }
}
