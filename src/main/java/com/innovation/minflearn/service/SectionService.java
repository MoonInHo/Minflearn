package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.exception.custom.course.CourseNotFoundException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Transactional
    public void addSection(
            Long courseId,
            String authorizationHeader,
            AddSectionRequestDto addSectionRequestDto
    ) {
        validateCourseExistence(courseId);

        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        SectionNumber lastSectionNumber = sectionRepository.getLastSectionNumber(courseId);
        SectionNumber sectionNumber = SectionNumber.getNextSection(lastSectionNumber);
        sectionRepository.save(addSectionRequestDto.toEntity(sectionNumber, courseId, memberId));
    }

    private void validateCourseExistence(Long courseId) {
        boolean courseExist = courseRepository.isCourseExist(courseId);
        if (!courseExist) {
            throw new CourseNotFoundException();
        }
    }
}
