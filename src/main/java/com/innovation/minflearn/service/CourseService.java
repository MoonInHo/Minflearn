package com.innovation.minflearn.service;

import com.innovation.minflearn.repository.cource.CourseRepository;
import com.innovation.minflearn.dto.request.CreateCourseRequestDto;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.repository.member.MemberRepository;
import com.innovation.minflearn.vo.member.Email;
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
        Long memberId = getMemberId(authorizationHeader);
        //TODO 추후 사용자 정보 추출 로직을 분리할 방법 고민 (ex. AOP, 인터셉터, 커스텀 어노테이션 등)
        courseRepository.save(createCourseRequestDto.toEntity(memberId));
    }

    private Long getMemberId(String authorizationHeader) {
        String email = jwtAuthProvider.extractEmail(authorizationHeader);
        return memberRepository.getMemberId(Email.of(email));
    }
}
