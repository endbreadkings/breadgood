package com.bside.breadgood.ddd.bakery.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.bakery.application.BakeryService;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryAdminRequestDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author : haedoang
 * date : 2022/06/08
 * description :
 */
@Api(value = "빵집 관리자 API", description = "[관리자] 빵집 API's")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BakeryAdminController {
    private final BakeryService bakeryService;

    @ApiOperation(value = "등록된 빵집 조회", notes = "조회 성공시 BakeryManagementResponseDto 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공시  BakeryManagementResponseDto 반환",
                    response = BakeryAdminRequestDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/bakery/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BakeryAdminRequestDto>> findAll() {
        return ResponseEntity.ok().body(bakeryService.findAll());
    }

    @ApiOperation(value = "빵집을 삭제한다", notes = "삭제 성공 시 200 응답 코드로 리턴합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공시 아무것도 반환하지 않습니다"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @DeleteMapping("/bakery/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bakeryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
