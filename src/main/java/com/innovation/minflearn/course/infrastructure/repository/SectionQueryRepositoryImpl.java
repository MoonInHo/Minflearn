package com.innovation.minflearn.course.infrastructure.repository;

import com.innovation.minflearn.course.domain.enums.SectionNumber;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.minflearn.course.domain.entity.QSection.section;

@Repository
@RequiredArgsConstructor
public class SectionQueryRepositoryImpl implements SectionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isSectionExist(Long courseId, SectionNumber sectionNumber) {
        return queryFactory
                .selectOne()
                .from(section)
                .where(
                        section.courseId.eq(courseId),
                        section.sectionNumber.eq(sectionNumber)
                )
                .fetchFirst() != null;
    }
}
