package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
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
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        verifyCourseOwnership(courseId, memberId);

        SectionNumber lastSectionNumber = sectionRepository.getLastSectionNumber(courseId);
        SectionNumber sectionNumber = SectionNumber.getNextSection(lastSectionNumber);
        sectionRepository.save(addSectionRequestDto.toEntity(sectionNumber, courseId));
    }

    private void verifyCourseOwnership(Long courseId, Long memberId) {
        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }
}
