package com.bside.breadgood.ddd.users.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.authentication.CurrentUser;
import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(value = "user me", description = "나의 회원 정보 관련 API's")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserMeController {

    private final UserService userService;

    // 별명 중복 체크 나의 별명 제외
    @ApiOperation(value = "나의 별명을 제외하고 별명이 중복되었는지 체크합니다.", notes = "별명이 중복될 경우 true || 중복되지 않았을 경우 false", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "별명이 중복될 경우 true || 중복되지 않았을 경우 false", response = Boolean.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PostMapping("/me/duplicate/nickName/{nickName}")
    public boolean duplicateNickNameExceptMyNickName(@ApiParam(value = "중복 체크 대상인 별명") @PathVariable String nickName, @CurrentUser UserPrincipal userPrincipal) {

//        userIdValidate(userId, userPrincipal);

        return userService.duplicateNickName(nickName, userPrincipal.getId());
    }

    // 나의 정보 조회
    @ApiOperation(value = "나의 정보를 조회합니다.", notes = "", response = UserResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회했을 경우 UserResponseDto 반환", response = UserResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/me")
    public UserInfoResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

//        userIdValidate(userId, userPrincipal);

        return userService.findUserInfoById(userPrincipal.getId());
    }

    // 별명 수정하기
    @ApiOperation(value = "별명 수정하기", notes = "", response = UserResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회했을 경우 UserResponseDto 반환", response = UserResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PatchMapping("/me/nickName/{nickName}")
    public UserResponseDto updateNickName(@ApiParam(value = "변경할 별명") @PathVariable String nickName,
                                          @CurrentUser UserPrincipal userPrincipal) {

//        userIdValidate(userId, userPrincipal);
        return userService.updateNickName(userPrincipal.getId(), nickName);

    }


    // 최애 빵 수정하기
    @ApiOperation(value = "최애빵 수정하기", notes = "최애 빵을 수정하면서 같이 유저의 프로필 사진도 수정 합니다.", response = UserResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회했을 경우 UserResponseDto 반환", response = UserResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @PatchMapping("/me/breadStyle/{breadStyleId}")
    public UserResponseDto updateBreadStyle(@ApiParam(value = "선택한 최애빵 스타일 아이디") @PathVariable Long breadStyleId,
                                            @CurrentUser UserPrincipal userPrincipal) {

//        userIdValidate(userPrincipal.getId(), userPrincipal);

        return userService.updateBreadStyleId(userPrincipal.getId(), breadStyleId);
    }


    // 회원 탈퇴 하기.
    @ApiOperation(value = "회원 탈퇴하기", notes = "기존에 회원 테이블에서 회원을 삭제하고, 탈퇴한 회원의 아이디를 담고 있는 테이블의 저장 후 저장된 아이디 값을 반환합니다.", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 탈퇴 했을 경우, 탈퇴한 회원 아이디 반환", response = Long.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @DeleteMapping("/me/withdrawal")
    public Long withdrawal(@CurrentUser UserPrincipal userPrincipal) {
        return userService.withdrawal(userPrincipal.getId());
    }


    // 자신의 userid 와 인증된 유저의 유저아이디가 같은지 확인
//    private void userIdValidate(Long userId, UserPrincipal userPrincipal) {
//        if (userId.compareTo(userPrincipal.getId()) != 0) {
//            throw new IllegalArgumentException("잘못된 유저 아이디 입니다.");
//        }
//    }
}
