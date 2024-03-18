package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{courseId}/lecture")
public class LectureRestController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<Void> addLecture(
            @PathVariable(name = "courseId") Long courseId,
            @RequestPart("lectureFile") MultipartFile lectureFile,
            @RequestPart("lectureData") AddLectureRequestDto addLectureRequestDto
    ) throws IOException {
        lectureService.addLecture(courseId, lectureFile, addLectureRequestDto);
        return ResponseEntity.ok().build();
    }
}
