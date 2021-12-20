package com.bside.breadgood.s3.ui;


import com.bside.breadgood.apifirstdesign.models.BadRequestError;
import com.bside.breadgood.apifirstdesign.models.InternalServerError;
import com.bside.breadgood.common.exception.ExceptionResponse;
import com.bside.breadgood.jwt.ui.dto.TokenRefreshResponse;
import com.bside.breadgood.s3.application.S3Service;
import com.bside.breadgood.s3.application.dto.S3UploadResponseDto;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "S3Upload", description = "S3 업로드 테스트 API's")
@Controller
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;


    @ApiOperation(value = "업로드 샘플 페이지로 이동 합니다.")
    @GetMapping("/api/v1/s3/test/page")
    public String gallery() {
        return "s3";
    }


    @ApiOperation(value = "파일을 업로드 합니다", notes = " 최대 5MB 까지 업로드 할 수 있습니다.\n swagger 에서는 실제로 s3 로 업로드가 안되서 response 에 filePaths 는 빈값이 나올 예정입니다. \n 200 일시 example 을 참고해주세요 ! ", httpMethod = "POST", consumes = "multipart/form-data", response = S3UploadResponseDto.class)
    @ApiParam(allowMultiple = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 업로드시 S3UploadResponseDto 반환", response = S3UploadResponseDto.class),
            @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
            @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class),
            @ApiResponse(code = -1, message = "ExceptionResponse", response = ExceptionResponse.class)}
    )
    @PostMapping(value = "/api/v1/s3/test/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
//            ,produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<S3UploadResponseDto> execWrite(
            @ApiParam(value = "image", required = true) @RequestPart(value = "image") MultipartFile[] files) {
        S3UploadResponseDto s3UploadResponseDto = s3Service.upload(files, "test");
        return ResponseEntity.ok(s3UploadResponseDto);
    }

}