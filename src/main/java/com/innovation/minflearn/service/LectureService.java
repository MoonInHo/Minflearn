package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.exception.custom.section.SectionNotFoundException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public void addLecture(
            Long courseId,
            Long sectionId,
            String authorizationHeader,
            AddLectureRequestDto addLectureRequestDto
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        verifyCourseOwnership(courseId, memberId);
        validateSectionExistence(sectionId);

        lectureRepository.save(addLectureRequestDto.toEntity(sectionId, memberId));
    }

    private void verifyCourseOwnership(Long courseId, Long memberId) {
        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }

    private void validateSectionExistence(Long sectionId) {
        boolean sectionExist = sectionRepository.isSectionExist(sectionId);
        if (!sectionExist) {
            throw new SectionNotFoundException();
        }
    }

}
