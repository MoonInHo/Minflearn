package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.service.LectureService;
import com.innovation.minflearn.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sections/{sectionId}/lectures")
public class LectureRestController {

    private final VideoValidator videoValidator;
    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<Void> addLecture(
            @PathVariable(name = "sectionId") Long sectionId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "lectureFile") MultipartFile lectureFile,
            @RequestPart(name = "lectureData") AddLectureRequestDto addLectureRequestDto
    ) throws IOException {
        videoValidator.validateVideoFile(lectureFile);
        lectureService.addLecture(sectionId, authorizationHeader, lectureFile, addLectureRequestDto);
        return ResponseEntity.ok().build();
    }
}
