package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.dto.request.EditLectureRequestDto;
import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.exception.custom.lecture.LectureNotFoundException;
import com.innovation.minflearn.exception.custom.lecturefile.LectureFileNotFoundException;
import com.innovation.minflearn.exception.custom.section.SectionNotFoundException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.vo.lecture.LectureContent;
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
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public void addLecture(
            Long courseId,
            Long sectionId,
            String authorizationHeader,
            AddLectureRequestDto addLectureRequestDto
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        verifyCourseOwnership(courseId, memberId);
        validateSectionExistence(courseId, sectionId);

        lectureRepository.save(addLectureRequestDto.toEntity(sectionId));
    }

    @Transactional
    public void editLecture(
            Long courseId,
            Long sectionId,
            Long lectureId,
            String authorizationHeader,
            EditLectureRequestDto editLectureRequestDto
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        Long lectureFileId = editLectureRequestDto.lectureFileId();

        verifyCourseOwnership(courseId, memberId);
        validateLectureExistence(sectionId, lectureId);
        validateLectureFileExistence(lectureFileId);

        LectureEntity lectureEntity = lectureRepository.getLectureEntity(lectureId);

        lectureEntity.editLectureContent(LectureContent.of(editLectureRequestDto.lectureContent()), lectureFileId);
    }

    private void verifyCourseOwnership(Long courseId, Long memberId) {
        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }

    private void validateSectionExistence(Long courseId, Long sectionId) {
        boolean sectionExist = sectionRepository.isSectionExist(courseId, sectionId);
        if (!sectionExist) {
            throw new SectionNotFoundException();
        }
    }

    private void validateLectureExistence(Long sectionId, Long lectureId) {
        boolean lectureExist = lectureRepository.isLectureExist(sectionId, lectureId);
        if (!lectureExist) {
            throw new LectureNotFoundException();
        }
    }

    private void validateLectureFileExistence(Long lectureFileId) {
        boolean lectureFileExist = lectureFileRepository.isLectureFileExist(lectureFileId);
        if (!lectureFileExist) {
            throw new LectureFileNotFoundException();
        }
    }
}
