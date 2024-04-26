package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/sections/{sectionId}/lectures")
public class LectureRestController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<Void> addLecture(
            @PathVariable(name = "courseId") Long courseId,
            @PathVariable(name = "sectionId") Long sectionId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestBody AddLectureRequestDto addLectureRequestDto
    ) {
        lectureService.addLecture(courseId, sectionId, authorizationHeader, addLectureRequestDto);
        return ResponseEntity.ok().build();
    }
}
