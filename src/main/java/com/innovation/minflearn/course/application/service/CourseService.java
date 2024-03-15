package com.innovation.minflearn.course.application.service;

import com.innovation.minflearn.course.application.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.course.domain.repository.CourseRepository;
import com.innovation.minflearn.member.application.security.JwtAuthProvider;
import com.innovation.minflearn.member.domain.repository.MemberRepository;
import com.innovation.minflearn.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final JwtAuthProvider jwtAuthProvider;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void createCourse(
            CreateCourseRequestDto createCourseRequestDto,
            String authorizationHeader
    ) {
        String email = jwtAuthProvider.extractEmail(authorizationHeader); //TODO 추후 사용자 정보 추출 로직을 분리할 방법 고민 (ex. AOP, 인터셉터, 커스텀 어노테이션 등)
        Long memberId = memberRepository.getMemberId(Email.of(email));

        courseRepository.save(createCourseRequestDto.toEntity(memberId));
    }
}
