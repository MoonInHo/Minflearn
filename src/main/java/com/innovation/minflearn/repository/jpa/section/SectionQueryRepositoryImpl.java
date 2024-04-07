package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.enums.SectionNumber;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    private BooleanExpression isCourseIdEquals(Long courseId) {
        return sectionEntity.courseId.eq(courseId);
    }

    private BooleanExpression isSectionIdEquals(Long sectionId) {
        return sectionEntity.id.eq(sectionId);
    }
}
