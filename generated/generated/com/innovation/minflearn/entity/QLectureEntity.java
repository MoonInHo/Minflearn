package com.innovation.minflearn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureEntity is a Querydsl query type for LectureEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLectureEntity extends EntityPathBase<LectureEntity> {

    private static final long serialVersionUID = 1284003408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureEntity lectureEntity = new QLectureEntity("lectureEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.innovation.minflearn.vo.lecture.QLectureTitle lectureTitle;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> sectionId = createNumber("sectionId", Long.class);

    public QLectureEntity(String variable) {
        this(LectureEntity.class, forVariable(variable), INITS);
    }

    public QLectureEntity(Path<? extends LectureEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureEntity(PathMetadata metadata, PathInits inits) {
        this(LectureEntity.class, metadata, inits);
    }

    public QLectureEntity(Class<? extends LectureEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lectureTitle = inits.isInitialized("lectureTitle") ? new com.innovation.minflearn.vo.lecture.QLectureTitle(forProperty("lectureTitle")) : null;
    }

}

