package com.bside.breadgood.ddd.emoji.ui;

import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "emoji", description = "이모지 API's")
@RestController
@RequestMapping("/api/v1/emoji")
@RequiredArgsConstructor
public class EmojiController {

    private final EmojiService emojiService;

    @ApiOperation(value = "이모지 리스트를 조회 합니다.", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회시 EmojiResponseDto 반환", response = EmojiResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @ApiImplicitParams({
    })
    @GetMapping("/list")
    public List<EmojiResponseDto> findAll() {
        return emojiService.findAll();
    }
}
