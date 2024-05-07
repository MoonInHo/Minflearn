package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.enums.SectionNumber;
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
                .where(sectionEntity.courseId.eq(courseId))
                .orderBy(sectionEntity.id.desc())
                .fetchFirst();
    }

    @Override
    public boolean isSectionExist(Long sectionId) {
        return queryFactory
                .selectOne()
                .from(sectionEntity)
                .where(sectionEntity.id.eq(sectionId))
                .fetchFirst() != null;
    }
}
