package com.innovation.minflearn.service;

import com.innovation.minflearn.document.CourseDocument;
import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.dto.response.CourseDetailResponseDto;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import com.innovation.minflearn.entity.CourseEntity;
import com.innovation.minflearn.exception.custom.course.CourseNotFoundException;
import com.innovation.minflearn.repository.elasticsearch.CourseSearchRepository;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final CourseSearchRepository courseSearchRepository;

    @Transactional
    public void createCourse(
            CreateCourseRequestDto createCourseRequestDto,
            String authorizationHeader
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        CourseEntity courseEntity = courseRepository.save(createCourseRequestDto.toEntity(memberId));
        courseSearchRepository.save(CourseDocument.of(courseEntity));
    }

    @Transactional(readOnly = true)
    public Page<GetCourseResponseDto> getCourses(String keyword, Pageable pageable) {
        if (isExistKeyword(keyword)) {
            return courseSearchRepository.findByCourseTitleOrInstructor(keyword, pageable);
        }
        return courseRepository.getCourses(pageable);
    }

    @Transactional(readOnly = true)
    public CourseDetailResponseDto getCourseDetail(Long courseId) {
        return courseRepository.getCourseDetail(courseId)
                .orElseThrow(CourseNotFoundException::new);
    }

    private boolean isExistKeyword(String keyword) {
        return keyword != null && !keyword.isBlank();
    }
}
