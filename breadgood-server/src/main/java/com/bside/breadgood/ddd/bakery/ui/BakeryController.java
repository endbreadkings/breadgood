package com.bside.breadgood.ddd.bakery.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.authentication.CurrentUser;
import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakery.application.BakeryService;
import com.bside.breadgood.ddd.bakery.application.dto.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "빵집 관련 API ", description = "빵집 API's")
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
@RestController
public class BakeryController {

    private final BakeryService bakeryService;

    @ApiParam(allowMultiple = true)
    @ApiOperation(value = "최애 빵집을 등록합니다.", notes = "등록 성공시 빵집 아이디 반환", response = Long.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "등록 성공시 아이디 반환", response = Long.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> save(
            @RequestPart("files") MultipartFile[] files,
            @ModelAttribute BakerySaveRequestDto requestDto,
            @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bakeryService.save(userPrincipal.getId(), requestDto, files));

    }


    @ApiOperation(value = "최애 빵집 리뷰를 등록합니다.", notes = "등록 성공시 ture 반환", response = Boolean.class, consumes = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "등록 성공시 ture 반환", response = Boolean.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping(value = "/{bakeryId}/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean addReview(@PathVariable Long bakeryId,
                             @ModelAttribute BakeryReviewRequestDto dto,
                             @RequestPart(value = "files", required = false) MultipartFile[] files,
                             @CurrentUser UserPrincipal userPrincipal) {
        return bakeryService.addReview(bakeryId, userPrincipal.getId(), dto, files);
    }


    @ApiOperation(value = "최애빵집 중복 체크", notes = "해당 도로명 주소가 최애 빵집으로 등록되어있는지 확인", response = CheckDuplicateBakeryResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "중복 되어 있을 경우 isDuplicate True 아닐 경우 False 이며 CheckDuplicateBakeryResponseDto 반환", response = CheckDuplicateBakeryResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/duplicate/roadAddress/{roadAddress}")
    public ResponseEntity<?> checkDuplicateByRoadAddress(@PathVariable String roadAddress) {
        return ResponseEntity.ok(bakeryService.checkDuplicateByRoadAddress(roadAddress));
    }


    @ApiOperation(value = "등록된 빵집 검색", notes = "등록 성공시 BakerySearchResponseDto 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "등록 성공시 BakerySearchResponseDto 반환",
                    response = BakerySearchResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/search")
    public List<BakerySearchResponseDto> search(@RequestBody(required = false) BakerySearchRequestDto dto) {
        return bakeryService.search(dto);
    }


    @ApiOperation(value = "해당 빵집 조회", notes = "조회 성공시 BakeryResponseDto 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "등록 성공시 BakeryResponseDto 반환",
                    response = BakeryResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/{bakeryId:\\d+}")
    public BakeryResponseDto findById(@PathVariable Long bakeryId, @CurrentUser UserPrincipal userPrincipal) {
        return bakeryService.findByIdAndUserId(bakeryId, userPrincipal.getId());
    }


}
