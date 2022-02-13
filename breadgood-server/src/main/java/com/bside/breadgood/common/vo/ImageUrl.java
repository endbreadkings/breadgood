package com.bside.breadgood.common.vo;

import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.util.FileUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl {

    private static final Pattern URL_PATTERN = Pattern.compile("^(?:https?:\\/\\/)[a-zA-Z0-9./_]+$");
    private static final String URL_ERROR_MESSAGE = "유효하지 않은 URL 입니다.";
    private static final String EMPTY_ERROR_MESSAGE = "이미지 URL 이 빈값입니다.";

    @Column
    private String imgUrl;

    private ImageUrl(String imgUrl) {
        validate(imgUrl);
        this.imgUrl = imgUrl;
    }

    private void validate(String imgUrl) {
        if (!StringUtils.hasText(imgUrl)) {
            throw new EmptyException(EMPTY_ERROR_MESSAGE);
        }

        if (!URL_PATTERN.matcher(imgUrl).matches()) {
            throw new IllegalImageUrlException(URL_ERROR_MESSAGE);
        }

        FileUtils.validateFileExtension(imgUrl);
    }

    public static ImageUrl from(String imgUrl) {
        return new ImageUrl(imgUrl);
    }
}
