package com.innovation.minflearn.repository.cource;

import com.innovation.minflearn.dto.response.CourseDetailResponseDto;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseQueryRepository {

    Page<GetCourseResponseDto> getCourses(Pageable pageable);

    Optional<CourseDetailResponseDto> getCourseDetail(Long courseId);

    List<GetCourseResponseDto> getCourses(String keyword);
}
