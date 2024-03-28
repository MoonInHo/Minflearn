package com.innovation.minflearn.repository.elasticsearch;

import com.innovation.minflearn.document.CourseDocument;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseDocument, Long> {

    Page<GetCourseResponseDto> findByCourseTitleOrInstructor(String courseTitle, String instructor, Pageable pageable);
}
