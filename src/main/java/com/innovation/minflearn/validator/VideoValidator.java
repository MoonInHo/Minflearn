package com.innovation.minflearn.validator;

import com.innovation.minflearn.exception.custom.lecture.UnsupportedVideoFileExtensionException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class VideoValidator { //EnumClass를 이용하는 방법 고려

    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList(".mp4", ".avi", ".mov", ".wmv", ".flv", ".mkv");

    public void validateVideoFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        if (!isFileNameExist(fileName)) {
            throw new IllegalArgumentException("파일명이 존재하지 않습니다.");
        }

        if (!isContainsVideoFileExtension(extractFileExtension(fileName))) {
            throw new UnsupportedVideoFileExtensionException();
        }
    }

    public static String extractFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex);
    }

    private boolean isContainsVideoFileExtension(String extension) {
        return ALLOWED_VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }

    private boolean isFileNameExist(String fileName) {
        return fileName != null && !fileName.isBlank();
    }
}
