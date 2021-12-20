package com.bside.breadgood.jwt.ui.controller;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.jwt.application.TokenUpdateService;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshRequest;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "TokenUpdate",description = "토큰 업데이트 API's")
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@RestController
public class JwtTokenController {

    private final TokenUpdateService tokenUpdateService;


    @ApiOperation(value = "토큰을 갱신합니다.", notes = "액세스 토큰 재발급 단,  리프레시 토큰이 액세스토큰 만료시간보다 더 적게 남아 있을 경우, " +
            "리프레시 토큰 재발급", response = TokenRefreshResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 변경시 TokenRefreshResponse 반환", response = TokenRefreshResponse.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@ApiParam(value = "재발급을 하기 위한 리프레시 토큰", required = true) @Valid @RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(tokenUpdateService.refresh(request));
    }

}