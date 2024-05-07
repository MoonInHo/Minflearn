package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.innovation.minflearn.entity.QLectureEntity.lectureEntity;

@Repository
@RequiredArgsConstructor
public class LectureQueryRepositoryImpl implements LectureQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<LectureEntity> getLectureEntity(Long lectureId) {
        return Optional.ofNullable(
                queryFactory
                .selectFrom(lectureEntity)
                .where(lectureEntity.id.eq(lectureId))
                .fetchOne()
        );
    }
}
