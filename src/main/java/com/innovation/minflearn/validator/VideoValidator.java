package com.innovation.minflearn.validator;

import com.innovation.minflearn.exception.custom.lecture.InvalidExtensionException;
import com.innovation.minflearn.exception.custom.lecture.UnsupportedVideoFileExtensionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class VideoValidator {

    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv");

    public void validateVideoFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("비디오 파일을 첨부해주세요.");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension == null) {
            throw new InvalidExtensionException();
        }

        if (!isContainsVideoFileExtension(extension)) {
            throw new UnsupportedVideoFileExtensionException();
        }
    }

    private boolean isContainsVideoFileExtension(String extension) {
        return ALLOWED_VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }
}
