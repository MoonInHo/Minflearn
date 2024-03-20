package com.innovation.minflearn.repository.section;

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
}
