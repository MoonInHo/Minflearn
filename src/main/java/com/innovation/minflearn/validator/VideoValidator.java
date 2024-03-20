package com.innovation.minflearn.validator;

import com.innovation.minflearn.exception.custom.lecture.InvalidExtensionException;
import com.innovation.minflearn.exception.custom.lecture.UnsupportedVideoFileExtensionException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class VideoValidator {

    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv");

    public void validateVideoFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("파일명이 존재하지 않습니다.");
        }

        String extension = getFileExtension(fileName);
        if (!isContainsVideoFileExtension(extension)) {
            throw new UnsupportedVideoFileExtensionException();
        }
    }

    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex <= 0 && lastDotIndex >= fileName.length() - 1) {
            throw new InvalidExtensionException();
        }
        return fileName.substring(lastDotIndex + 1);
    }

    private boolean isContainsVideoFileExtension(String extension) {
        return ALLOWED_VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }
}
