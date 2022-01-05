package com.bside.breadgood.util;

import com.bside.breadgood.common.exception.IllegalFileExtensionException;

import java.util.Set;

public class FileUtils {
    private static final String EXTENSION_ERROR_MESSAGE = "지원하지 않는 파일 확장자 입니다.";

    private FileUtils() {
    }

    private static final String EXTENSION_DELIMITER = ".";
    private static final Set<String> AVAILABLE_EXTENSION_GROUP = Set.of("JPG", "JPEG", "PNG", "WEBP", "SVG");

    public static void validateFileExtension(String fileName) {
        final int extensionIndex = getExtensionIndex(fileName);
        final String extension = fileName.substring(extensionIndex);
        if (!isAvailableExtension(extension)) {
            throw new IllegalFileExtensionException(EXTENSION_ERROR_MESSAGE);
        }
    }

    private static boolean isAvailableExtension(String extension) {
        return AVAILABLE_EXTENSION_GROUP.contains(extension.toUpperCase());
    }

    private static int getExtensionIndex(String fileName) {
        return fileName.lastIndexOf(EXTENSION_DELIMITER) + 1;
    }
}
