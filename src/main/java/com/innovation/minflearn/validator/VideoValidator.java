package com.innovation.minflearn.validator;

import com.innovation.minflearn.exception.custom.lecture.FileIntegrityViolationException;
import com.innovation.minflearn.exception.custom.lecture.InvalidExtensionException;
import com.innovation.minflearn.exception.custom.lecture.UnsupportedVideoFileExtensionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Component
public class VideoValidator {

    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv");

    public void validateVideoFile(MultipartFile file, String expectedHash) throws IOException, NoSuchAlgorithmException {

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
        if (!isFileIntegrity(file, expectedHash)) {
            throw new FileIntegrityViolationException();
        }
    }

    private boolean isContainsVideoFileExtension(String extension) {
        return ALLOWED_VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private boolean isFileIntegrity(MultipartFile file, String expectedHash) throws IOException, NoSuchAlgorithmException {
        byte[] data = file.getBytes();
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
        String calculatedHash = bytesToHex(hash);
        return calculatedHash.equals(expectedHash);
    }
}
