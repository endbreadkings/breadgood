package com.bside.breadgood.ddd.users.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.authentication.CurrentUser;
import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.authentication.oauth2.dto.Oauth2UserSingUpRequestDto;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.LoginRequest;
import com.bside.breadgood.jwt.application.AccessTokenService;
import com.bside.breadgood.jwt.application.RefreshTokenService;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(value = "users", description = "회원 관련 API's")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;

    @ApiOperation(value = "소셜 회원 회원가입 입니다.", notes = "", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 회원가입시 회원 아이디 반환", response = Long.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/social/signup")
    public Long oauth2UserSingUp(@ApiParam(value = "소셜 회원가입 하기 위한 사용자 입력 정보 ", required = true) @RequestBody Oauth2UserSingUpRequestDto dto,
                                 @CurrentUser UserPrincipal userPrincipal) {
        return userService.socialGuestSignUp(userPrincipal.getId(), dto);
    }

    @ApiOperation(value = "회원 로그인을 합니다.", notes = "로그인 성공 시 TokenRefreshResponse 타입 반환 [유저]:test@breadgood.com//1234", response = TokenRefreshResponse.class)
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )

        );

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


    // 별명 중복 체크
    @ApiOperation(value = "별명이 중복되었는지 체크합니다.", notes = "별명이 중복될 경우 true || 중복되지 않았을 경우 false", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "별명이 중복될 경우 true || 중복되지 않았을 경우 false", response = Boolean.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/duplicate/nickName/{nickName}")
    public boolean duplicateNickName(@ApiParam(value = "중복 체크 대상인 별명") @PathVariable String nickName) {
        return userService.duplicateNickName(nickName);
    }
}
