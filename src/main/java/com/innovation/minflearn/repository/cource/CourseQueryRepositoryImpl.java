package com.innovation.minflearn.repository.cource;

import com.innovation.minflearn.dto.response.GetCourseResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.innovation.minflearn.entity.QCourseEntity.courseEntity;

@Repository
@RequiredArgsConstructor
public class CourseQueryRepositoryImpl implements CourseQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetCourseResponseDto> getCourses(Pageable pageable) {

        List<GetCourseResponseDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                GetCourseResponseDto.class,
                                courseEntity.courseTitle.courseTitle,
                                courseEntity.instructor.Instructor,
                                courseEntity.price.price
                        )
                )
                .from(courseEntity)
                .fetch();

        Long totalCount = Optional.ofNullable(
                queryFactory
                        .select(courseEntity.count())
                        .from(courseEntity)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(fetch, pageable, totalCount);
    }
}
