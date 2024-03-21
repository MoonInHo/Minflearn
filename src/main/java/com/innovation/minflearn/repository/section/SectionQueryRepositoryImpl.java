package com.innovation.minflearn.repository.section;

import com.innovation.minflearn.dto.LectureDto;
import com.innovation.minflearn.dto.SectionDto;
import com.innovation.minflearn.enums.SectionNumber;
import com.querydsl.core.types.Projections;
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
                        sectionEntity.sectionNumber.eq(sectionNumber),
                        sectionEntity.courseId.eq(courseId),
                        sectionEntity.memberId.eq(memberId)
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
                .where(sectionEntity.courseId.eq(courseId))
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
                .where(lectureEntity.sectionId.eq(sectionId))
                .fetch();
    }
}
