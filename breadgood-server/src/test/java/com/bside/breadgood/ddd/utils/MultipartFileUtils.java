package com.bside.breadgood.ddd.utils;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * create on 2022/06/05. create by IntelliJ IDEA.
 *
 * <p> Multipart 관련 테스트 유틸 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
public class MultipartFileUtils {

  public static MultiPartSpecification getMultiPartSpecification(String name, MultipartFile multipartFile) throws IOException {
    return new MultiPartSpecBuilder(multipartFile.getBytes()).
        fileName(multipartFile.getOriginalFilename()).
        controlName(name).
        mimeType(multipartFile.getContentType()).
        build();
  }
}
