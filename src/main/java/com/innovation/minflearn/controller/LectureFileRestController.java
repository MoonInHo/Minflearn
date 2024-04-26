package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.service.LectureFileService;
import com.innovation.minflearn.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/lecture-file")
public class LectureFileRestController {

    private final VideoValidator videoValidator;
    private final LectureFileService lectureFileService;

    @PostMapping("/validate-video")
    public ResponseEntity<UploadLectureFileUrlResponseDto> createLectureFileMetadata(
            @PathVariable(name = "courseId") Long courseId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {
        videoValidator.validateVideoFile(file);
        return ResponseEntity.ok().body(
                lectureFileService.createFileMetadataAndTempDir(courseId, authorizationHeader, file)
        );
    }
}
