package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.dto.LectureQueryDto;
import com.innovation.minflearn.dto.SectionQueryDto;
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
    public boolean isSectionExist(SectionNumber sectionNumber, Long courseId, Long memberId) {
        return queryFactory
                .selectOne()
                .from(sectionEntity)
                .where(
                        isSectionNumberEquals(sectionNumber),
                        isCourseIdEquals(courseId),
                        isMemberIdEquals(memberId)
                )
                .fetchFirst() != null;
    }

    private BooleanExpression isSectionNumberEquals(SectionNumber sectionNumber) {
        return sectionEntity.sectionNumber.eq(sectionNumber);
    }

    @Override
    public List<SectionQueryDto> getSections(Long courseId) {

        List<SectionQueryDto> result = getSectionsTest(courseId);

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

    private List<SectionQueryDto> getSectionsTest(Long courseId) {
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

    private BooleanExpression isMemberIdEquals(Long memberId) {
        return sectionEntity.memberId.eq(memberId);
    }
}
