package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/sections")
public class SectionRestController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Void> addSection(
            @PathVariable(name = "courseId") Long courseId,
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestBody AddSectionRequestDto addSectionRequestDto
    ) {
        sectionService.addSection(courseId, authorizationHeader, addSectionRequestDto);
        return ResponseEntity.ok().build();
    }
}
