package com.innovation.minflearn.repository.cource;

import com.innovation.minflearn.dto.response.CourseDetailResponseDto;
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
                                courseEntity.instructor.instructor,
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

    @Override
    public Optional<CourseDetailResponseDto> getCourseDetail(Long courseId) {

        CourseDetailResponseDto result = queryFactory
                .select(
                        Projections.fields(
                                CourseDetailResponseDto.class,
                                courseEntity.courseTitle.courseTitle,
                                courseEntity.description.description,
                                courseEntity.category.occupation,
                                courseEntity.category.field,
                                courseEntity.category.skillTag,
                                courseEntity.instructor.instructor,
                                courseEntity.price.price,
                                courseEntity.courseDuration.courseDuration
                        )
                )
                .from(courseEntity)
                .where(courseEntity.id.eq(courseId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
