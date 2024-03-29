package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.exception.custom.course.CourseNotFoundException;
import com.innovation.minflearn.exception.custom.section.DuplicateSectionException;
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
        validateCourseExistence(courseId); //TODO section 이 순서대로 생성되도록 변경

        SectionNumber sectionNumber = SectionNumber.checkSection(addSectionRequestDto.getSectionNumber());
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        checkDuplicateSectionNumber(sectionNumber, courseId, memberId);

        sectionRepository.save(addSectionRequestDto.toEntity(sectionNumber, courseId, memberId));
    }

    private void validateCourseExistence(Long courseId) {
        boolean courseExist = courseRepository.existsById(courseId);
        if (!courseExist) {
            throw new CourseNotFoundException();
        }
    }

    private void checkDuplicateSectionNumber(SectionNumber sectionNumber, Long courseId, Long memberId) {
        boolean sectionExist = sectionRepository.isSectionExist(sectionNumber, courseId, memberId);
        if (sectionExist) {
            throw new DuplicateSectionException();
        }
    }
}
