package com.bside.breadgood.ddd.bakerycategory.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryRequestDto;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(value = "bakeryCategory", description = "[관리자] 빵집 카테고리 API's")
@RestController
@RequestMapping("/api/v1/admin/bakeryCategory")
@RequiredArgsConstructor
public class AdminBakeryCategoryController {

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


    @ApiParam(allowMultiple = true)
    @ApiOperation(value = "빵집 카테고리를 등록 합니다.", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 BakeryCategoryResponseDto 반환", response = BakeryCategoryResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BakeryCategoryResponseDto saveBakeryCategory(
            @RequestPart("titleColoredImg") MultipartFile titleColoredImg,
            @RequestPart("titleUncoloredImg") MultipartFile titleUncoloredImg,
            @RequestPart("markerImg") MultipartFile markerImg,
            @RequestParam("color") String color,
            @RequestParam("content") String content,
            @RequestParam("title") String title
    ) {
        return bakeryCategoryService.saveBakeryCategory(BakeryCategoryRequestDto.builder()
                .content(content)
                .titleColoredImg(titleColoredImg)
                .color(color)
                .title(title)
                .titleUncoloredImg(titleUncoloredImg)
                .markerImg(markerImg)
                .build());
    }

}
