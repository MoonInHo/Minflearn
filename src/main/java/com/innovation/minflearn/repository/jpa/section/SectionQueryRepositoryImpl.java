package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.dto.query.LectureQueryDto;
import com.innovation.minflearn.dto.query.SectionQueryDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.innovation.minflearn.entity.QLectureEntity.lectureEntity;
import static com.innovation.minflearn.entity.QSectionEntity.sectionEntity;

@Repository
@RequiredArgsConstructor
public class SectionQueryRepositoryImpl implements SectionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public SectionNumber getLastSectionNumber(Long courseId) {
        return queryFactory
                .select(sectionEntity.sectionNumber)
                .from(sectionEntity)
                .where(isCourseIdEquals(courseId))
                .orderBy(sectionEntity.id.desc())
                .fetchFirst();
    }

    @Override
    public boolean isSectionExist(Long sectionId) {
        return queryFactory
                .selectOne()
                .from(sectionEntity)
                .where(isSectionIdEquals(sectionId))
                .fetchFirst() != null;
    }

    @Override
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
                                lectureEntity.lectureTitle.lectureTitle,
                                lectureEntity.lectureDuration.lectureDuration
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
                .where(isCourseIdEquals(courseId))
                .fetch();
    }

    private BooleanExpression isCourseIdEquals(Long courseId) {
        return sectionEntity.courseId.eq(courseId);
    }

    private BooleanExpression isSectionIdEquals(Long sectionId) {
        return sectionEntity.id.eq(sectionId);
    }
}
