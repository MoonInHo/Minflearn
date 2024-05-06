package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.minflearn.entity.QLectureEntity.lectureEntity;

@Repository
@RequiredArgsConstructor
public class LectureQueryRepositoryImpl implements LectureQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public LectureEntity getLectureEntity(Long lectureId) {
        return queryFactory
                .selectFrom(lectureEntity)
                .where(lectureEntity.id.eq(lectureId))
                .fetchOne();
    }

    @Override
    public boolean isLectureExist(Long sectionId, Long lectureId) {
        return queryFactory
                .selectOne()
                .from(lectureEntity)
                .where(
                        lectureEntity.sectionId.eq(sectionId),
                        lectureEntity.id.eq(lectureId)
                )
                .fetchFirst() != null;
    }
}
