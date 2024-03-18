package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseRestController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> createCourse(
            @RequestBody CreateCourseRequestDto createCourseRequestDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        courseService.createCourse(createCourseRequestDto, authorizationHeader);
        return ResponseEntity.ok().build();
    }
}
