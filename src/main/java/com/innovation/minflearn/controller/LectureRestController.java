package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.service.LectureService;
import com.innovation.minflearn.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> addLecture(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "lectureData") AddLectureRequestDto addLectureRequestDto
    ) throws IOException {

        videoValidator.validateVideoFile(file);

        return lectureService.addLecture(authorizationHeader, file, addLectureRequestDto) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
}
