package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.dto.query.LectureFileQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.minflearn.entity.QLectureFileEntity.lectureFileEntity;

@Repository
@RequiredArgsConstructor
public class LectureFileQueryRepositoryImpl implements LectureFileQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public LectureFileQueryDto getFilename(Long lectureFileId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                LectureFileQueryDto.class,
                                lectureFileEntity.originFilename.originFilename,
                                lectureFileEntity.storedFilename.storedFilename
                        )
                )
                .from(lectureFileEntity)
                .where(lectureFileEntity.id.eq(lectureFileId))
                .fetchOne();
    }

    @Override
    public String getOriginFilename(Long lectureFileId) {
        return queryFactory
                .select(lectureFileEntity.originFilename.originFilename)
                .from(lectureFileEntity)
                .where(lectureFileEntity.id.eq(lectureFileId))
                .fetchOne();
    }

    @Override
    public String getStoredFilename(Long lectureFileId) {
        return queryFactory
                .select(lectureFileEntity.storedFilename.storedFilename)
                .from(lectureFileEntity)
                .where(lectureFileEntity.id.eq(lectureFileId))
                .fetchOne();
    }

    @Override
    public boolean isLectureFileExist(Long lectureFileId) {
        return queryFactory
                .selectOne()
                .from(lectureFileEntity)
                .where(lectureFileEntity.id.eq(lectureFileId))
                .fetchFirst() != null;
    }
}
