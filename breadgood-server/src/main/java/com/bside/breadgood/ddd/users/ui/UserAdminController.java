package com.bside.breadgood.ddd.users.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.users.application.dto.LoginRequest;
import com.bside.breadgood.ddd.users.infra.AdminAuthenticationValidator;
import com.bside.breadgood.jwt.application.AccessTokenService;
import com.bside.breadgood.jwt.application.RefreshTokenService;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "adminUsers", description = "관리자용 회원 관련 API's")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class UserAdminController {
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final AdminAuthenticationValidator authenticationValidator;

    @ApiOperation(value = "관리자 로그인 요청을 합니다.", notes = "로그인 성공 시 TokenRefreshResponse 타입 반환 [관리자]:admin@breadgood.com//1234", response = TokenRefreshResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 변경시 TokenRefreshResponse 반환", response = TokenRefreshResponse.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationValidator.validate(loginRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String accessToken = accessTokenService.createTokenByAuthentication(authentication);
        final Long accessTokenExpirationTimeMsec = accessTokenService.getExpirationTime(accessToken);
        final String refreshToken = refreshTokenService.createTokenByAuthentication(authentication);
        final Long refreshTokenExpirationTimeMsec = refreshTokenService.getExpirationTime(refreshToken);

        return ResponseEntity.ok(TokenRefreshResponse.builder()
                .accessTokenExpirationTimeMsec(accessTokenExpirationTimeMsec)
                .refreshTokenExpirationTimeMsec(refreshTokenExpirationTimeMsec)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build());
    }
}
