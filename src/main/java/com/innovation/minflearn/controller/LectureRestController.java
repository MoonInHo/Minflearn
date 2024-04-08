package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.dto.request.ChunkFileRequestDto;
import com.innovation.minflearn.service.LectureService;
import com.innovation.minflearn.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/sections/{sectionId}/lectures")
public class LectureRestController {

    private final VideoValidator videoValidator;
    private final LectureService lectureService;

    @PostMapping // Tus protocol 학습 후 적용 고민
    public ResponseEntity<Void> addLecture(
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "sectionId") Long sectionId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "expectedHash") String expectedHash,
            @RequestPart(name = "lectureData") AddLectureRequestDto addLectureRequestDto
    ) throws IOException, NoSuchAlgorithmException {

        videoValidator.validateVideoFile(file, expectedHash);

        lectureService.addLecture(courseId, sectionId, authorizationHeader, addLectureRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/lecture-chunk")
    public ResponseEntity<Void> uploadChunkFile( //TODO courseId, sectionId 활용 고민
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "sectionId") Long sectionId,
            @RequestPart(name = "file") MultipartFile chunkFile,
            @RequestPart(name = "chunkData") ChunkFileRequestDto chunkFileRequestDto
    ) throws IOException {

        return lectureService.uploadChunkFile(courseId, sectionId, chunkFile, chunkFileRequestDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/lecture-chunk")
    public ResponseEntity<?> getLastChunkNumber( //TODO courseId, sectionId 활용 고민
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "sectionId") Long sectionId
    ) {
        return ResponseEntity.ok(lectureService.getLastChunkNumber(courseId, sectionId));
    }
}
