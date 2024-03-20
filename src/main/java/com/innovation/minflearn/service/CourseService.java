package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.repository.cource.CourseRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;

    @Transactional
    public void createCourse(
            CreateCourseRequestDto createCourseRequestDto,
            String authorizationHeader
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        courseRepository.save(createCourseRequestDto.toEntity(memberId));
    }
}
