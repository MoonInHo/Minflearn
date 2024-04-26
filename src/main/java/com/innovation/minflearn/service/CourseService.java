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
    public void createCourse( // 강좌 생성에 실패한다면? // 도중에 실패한다면? // 다른 개발자가 코드를 착각할 가능성
            CreateCourseRequestDto createCourseRequestDto,
            String authorizationHeader
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        saveCourseInfo(createCourseRequestDto, memberId); // 메소드를 합치면서 생길 수 있는 문제 고민
    }

    @Transactional(readOnly = true)
    public Page<GetCourseResponseDto> getCourses(String keyword, Pageable pageable) {
        if (isExistKeyword(keyword)) {
            return courseSearchRepository.findByCourseTitleOrInstructor(keyword, pageable); //TODO 검색 키워드를 기준으로 서로 다른 DB를 조회 하면서 생기는 문제가 있는지 고민
        }
        return courseRepository.getCourses(pageable);
    }

    @Transactional(readOnly = true)
    public CourseDetailResponseDto getCourseDetail(Long courseId) {
        return courseRepository.getCourseDetail(courseId)
                .orElseThrow(CourseNotFoundException::new);
    }

    private void saveCourseInfo(CreateCourseRequestDto createCourseRequestDto, Long memberId) {
        CourseEntity courseEntity = courseRepository.save(createCourseRequestDto.toEntity(memberId));
        courseSearchRepository.save(CourseDocument.of(courseEntity));
    }

    private boolean isExistKeyword(String keyword) {
        return keyword != null && !keyword.isBlank();
    }
}
