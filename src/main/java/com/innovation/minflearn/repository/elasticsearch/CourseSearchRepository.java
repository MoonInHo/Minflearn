package com.innovation.minflearn.repository.elasticsearch;

import com.innovation.minflearn.document.CourseDocument;
import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseDocument, Long> {

    @Query("{\"bool\": {\"should\": [{\"match_phrase\": {\"courseTitle\": \"?0\"}},{\"match_phrase\": {\"instructor\": \"?0\"}}]}}")
    Page<GetCourseResponseDto> findByCourseTitleOrInstructor(String keyword, Pageable pageable); //TODO spring data elasticsearch @Query 문법 확인 후 custom query 작성
}
