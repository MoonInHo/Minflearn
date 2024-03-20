package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> createCourse(
            @RequestBody CreateCourseRequestDto createCourseRequestDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ) {
        courseService.createCourse(createCourseRequestDto, authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<GetCourseResponseDto>> getCourses(Pageable pageable) {
        return ResponseEntity.ok().body(courseService.getCourses(pageable));
    }
}
