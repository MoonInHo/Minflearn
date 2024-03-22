package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.SectionDto;
import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.dto.response.CourseDetailResponseDto;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import com.innovation.minflearn.exception.custom.course.CourseNotFoundException;
import com.innovation.minflearn.repository.cource.CourseRepository;
import com.innovation.minflearn.repository.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Transactional
    public void createCourse(
            CreateCourseRequestDto createCourseRequestDto,
            String authorizationHeader
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        courseRepository.save(createCourseRequestDto.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public Page<GetCourseResponseDto> getCourses(Pageable pageable) {
        return courseRepository.getCourses(pageable);
    }

    @Transactional(readOnly = true)
    public CourseDetailResponseDto getCourseDetail(Long courseId) {

        CourseDetailResponseDto courseDetailResponseDto = courseRepository.getCourseDetail(courseId)
                .orElseThrow(CourseNotFoundException::new);

        List<SectionDto> sections = sectionRepository.getSections(courseId);
        courseDetailResponseDto.includeSections(sections);

        return courseDetailResponseDto;
    }

    @Transactional(readOnly = true)
    public List<GetCourseResponseDto> searchCourses(String keyword) {
        return courseRepository.getCourses(keyword);
    }
}
