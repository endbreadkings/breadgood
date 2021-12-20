package com.bside.breadgood.s3.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class S3UploadResponseDto {

    @ApiModelProperty(position = 1, dataType = "String", example = "https://d74hbwjus7qtu.cloudfront.net/")
    private final String fileHost;

    @ApiModelProperty(position = 2, dataType = "List", example =  "[\"test/20210627/스크린샷 2021-06-23 오후 10.43.16.png\",\"test/20210627/test.png\"]" )
    private final List<String> filePaths;

    @Builder
    public S3UploadResponseDto(String fileHost, List<String> filePaths) {
        this.fileHost = fileHost;
        this.filePaths = filePaths;
    }

}
