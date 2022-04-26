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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@Api(value = "terms", description = "관리자 약관 관리 API's")
@RestController
@RequestMapping("/api/v1/admin/termsType")
@RequiredArgsConstructor
public class TermsAdminController {

    private final TermsTypeService termsTypeService;

    @ApiOperation(value = "", notes = "타입 아이디가 루트 아이디 입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 ActiveTermsResponseDto 반환", response = TermsTypeResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TermsTypeResponseDto> saveTermsType(@RequestBody TermsTypeSaveRequestDto request) {
        final TermsTypeResponseDto termsTypeResponseDto = termsTypeService.saveTerms(request);
        return ResponseEntity.created(URI.create("/admin/termsType/" + termsTypeResponseDto.getId()))
                .body(termsTypeResponseDto);
    }


}

