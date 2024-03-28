package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.dto.LectureDto;
import com.innovation.minflearn.dto.SectionDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<SectionDto> getSections(Long courseId) {
        List<SectionDto> sections = queryFactory
                .select(
                        Projections.fields(
                                SectionDto.class,
                                sectionEntity.id,
                                sectionEntity.sectionNumber,
                                sectionEntity.sectionTitle.sectionTitle,
                                sectionEntity.learningObjective.learningObjective
                        )
                )
                .from(sectionEntity)
                .where(isCourseIdEquals(courseId))
                .fetch();

        for (SectionDto section : sections) {
            List<LectureDto> lectures = projectionLecture(section.getId());
            section.includeLectures(lectures);
        }
        return sections;
    }

    private List<LectureDto> projectionLecture(Long sectionId) {
        return queryFactory
            .select(
                Projections.constructor(
                        LectureDto.class,
                        lectureEntity.lectureTitle.lectureTitle,
                        lectureEntity.lectureDuration.lectureDuration,
                        lectureEntity.unitId.unitId
                )
        )
                .from(lectureEntity)
                .where(isSectionIdEquals(sectionId))
                .fetch();
    }

    private BooleanExpression isSectionNumberEquals(SectionNumber sectionNumber) {
        return sectionEntity.sectionNumber.eq(sectionNumber);
    }

    private BooleanExpression isCourseIdEquals(Long courseId) {
        return sectionEntity.courseId.eq(courseId);
    }

    private BooleanExpression isMemberIdEquals(Long memberId) {
        return sectionEntity.memberId.eq(memberId);
    }

    private BooleanExpression isSectionIdEquals(Long sectionId) {
        return lectureEntity.sectionId.eq(sectionId);
    }
}
