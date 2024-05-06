package com.innovation.minflearn.repository.jpa.cource;

import com.innovation.minflearn.dto.query.LectureQueryDto;
import com.innovation.minflearn.dto.query.SectionQueryDto;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.innovation.minflearn.entity.QCourseEntity.courseEntity;
import static com.innovation.minflearn.entity.QLectureEntity.lectureEntity;
import static com.innovation.minflearn.entity.QSectionEntity.sectionEntity;

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

        List<SectionQueryDto> lecturesBySections = getLecturesBySections(courseId);

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

        if (result != null && !lecturesBySections.isEmpty()) {
            result.includeSections(lecturesBySections);
        }
        return Optional.ofNullable(result);
    }

    public List<SectionQueryDto> getLecturesBySections(Long courseId) {

        List<SectionQueryDto> result = getSections(courseId);

        List<Long> sectionIds = result.stream()
                .map(SectionQueryDto::getSectionId)
                .collect(Collectors.toList());

        List<LectureQueryDto> lectures = queryFactory
                .select(
                        Projections.constructor(
                                LectureQueryDto.class,
                                lectureEntity.sectionId,
                                lectureEntity.id.as("lectureId"),
                                lectureEntity.lectureTitle.lectureTitle
                        )
                )
                .from(lectureEntity)
                .join(sectionEntity)
                .on(lectureEntity.sectionId.eq(sectionEntity.id))
                .where(sectionEntity.id.in(sectionIds))
                .fetch();

        Map<Long, List<LectureQueryDto>> lectureMap = lectures.stream()
                .collect(Collectors.groupingBy(LectureQueryDto::sectionId));

        result.forEach(s -> s.includeLectures(lectureMap.get(s.getSectionId())));

        return result;
    }

    private List<SectionQueryDto> getSections(Long courseId) {
        return queryFactory
                .select(
                        Projections.fields(
                                SectionQueryDto.class,
                                sectionEntity.id.as("sectionId"),
                                sectionEntity.sectionNumber,
                                sectionEntity.sectionTitle.sectionTitle,
                                sectionEntity.learningObjective.learningObjective
                        )
                )
                .from(sectionEntity)
                .where(sectionEntity.courseId.eq(courseId))
                .fetch();
    }

    @Override
    public boolean isCourseOwner(Long courseId, Long memberId) {
        return queryFactory
                .selectOne()
                .from(courseEntity)
                .where(
                        courseEntity.id.eq(courseId),
                        courseEntity.memberId.eq(memberId)
                )
                .fetchFirst() != null;
    }
}
