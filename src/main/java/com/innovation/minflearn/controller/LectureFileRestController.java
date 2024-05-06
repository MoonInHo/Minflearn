package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;
import com.innovation.minflearn.dto.request.GetFailedChunkRequestDto;
import com.innovation.minflearn.dto.response.GetFailedChunkResponseDto;
import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.service.LectureFileUploadService;
import com.innovation.minflearn.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/lecture-file")
public class LectureFileRestController {

    private final VideoValidator videoValidator;
    private final LectureFileUploadService lectureFileUploadService;

    @PostMapping("/video")
    public ResponseEntity<UploadLectureFileUrlResponseDto> createLectureFileMetadata(
            @PathVariable(name = "courseId") Long courseId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {
        videoValidator.validateVideoFile(file);
        return ResponseEntity.ok()
                .body(lectureFileUploadService.createFileMetadataAndTempDir(courseId, authorizationHeader, file));
    }

    @PostMapping("/{lectureFileId}/chunks/{chunkNumber}")
    public ResponseEntity<Void> uploadChunkFile(
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "lectureFileId") Long lectureFileId,
            @PathVariable(name = "chunkNumber") int chunkNumber,
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "fileMetadata") ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException, NoSuchAlgorithmException {
        videoValidator.validateFileIntegrity(file, chunkFileUploadRequestDto.checksum());
        lectureFileUploadService.uploadChunkFile(courseId, lectureFileId, chunkNumber, file, chunkFileUploadRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{lectureFileId}/chunks/failed-chunks")
    public ResponseEntity<GetFailedChunkResponseDto> getUploadFailedChunkIndex(
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "lectureFileId") Long lectureFileId,
            @RequestBody GetFailedChunkRequestDto getFailedChunkRequestDto
    ) throws IOException {
        return ResponseEntity.ok().body(
                lectureFileUploadService.getUploadFailedChunkIndex(courseId, lectureFileId, getFailedChunkRequestDto)
        );
    }
}
