package com.bside.breadgood.ddd.termstype.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.termstype.ui.dto.ActiveTermsResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeSaveRequestDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@Api(value = "terms", description = "관리자 약관 관리 API's")
@RestController
@RequestMapping("/api/v1/admin/termsType")
@RequiredArgsConstructor
public class TermsAdminController {

    private final TermsTypeService termsTypeService;

    @ApiOperation(value = "약관을 등록합니다.", notes = "관리자 계정만 사용 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "등록 성공 시 TermsTypeResponseDto 반환", response = TermsTypeResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TermsTypeResponseDto> saveTermsType(@RequestBody TermsTypeSaveRequestDto request) {
        final TermsTypeResponseDto termsTypeResponseDto = termsTypeService.save(request);
        return ResponseEntity.created(URI.create("/admin/termsType/" + termsTypeResponseDto.getId()))
                .body(termsTypeResponseDto);
    }

    @ApiOperation(value = "집행중인 약관 리스트를 조회 합니다.", notes = "타입 아이디가 루트 아이디 입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 ActiveTermsResponseDto 반환", response = ActiveTermsResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public List<ActiveTermsResponseDto> findAll() {
        return termsTypeService.findActiveList();
    }
}

