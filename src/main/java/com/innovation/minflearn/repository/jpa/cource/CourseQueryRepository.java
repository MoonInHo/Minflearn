package com.innovation.minflearn.repository.jpa.cource;

import com.innovation.minflearn.dto.response.CourseDetailResponseDto;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseQueryRepository {

    Page<GetCourseResponseDto> getCourses(Pageable pageable);

    Optional<CourseDetailResponseDto> getCourseDetail(Long courseId);

    boolean isCourseOwner(Long courseId, Long memberId);

    boolean isCourseExist(Long courseId);
}
