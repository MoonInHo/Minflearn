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
@RequestMapping("/api/courses/lectures")
public class LectureRestController {

    private final VideoValidator videoValidator;
    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<Void> addLecture(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "lectureFile") MultipartFile file,
            @RequestPart(name = "lectureData") AddLectureRequestDto addLectureRequestDto
    ) throws IOException {
        videoValidator.validateVideoFile(file);
        lectureService.addLecture(authorizationHeader, file, addLectureRequestDto);
        return ResponseEntity.ok().build();
    }
}
