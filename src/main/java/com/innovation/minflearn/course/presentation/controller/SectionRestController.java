package com.innovation.minflearn.course.presentation.controller;

import com.innovation.minflearn.course.application.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.course.application.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{courseId}/sections")
public class SectionRestController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Void> addSection(
            @PathVariable(name = "courseId") Long courseId,
            @RequestBody AddSectionRequestDto addSectionRequestDto
    ) {
        sectionService.addSection(courseId, addSectionRequestDto);
        return ResponseEntity.ok().build();
    }
}
