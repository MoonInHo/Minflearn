package com.innovation.minflearn.repository.cource;

import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseQueryRepository {

    Page<GetCourseResponseDto> getCourses(Pageable pageable);
}
