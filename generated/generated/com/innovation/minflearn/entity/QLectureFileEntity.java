package com.innovation.minflearn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureFileEntity is a Querydsl query type for LectureFileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLectureFileEntity extends EntityPathBase<LectureFileEntity> {

    private static final long serialVersionUID = 881667180L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureFileEntity lectureFileEntity = new QLectureFileEntity("lectureFileEntity");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.innovation.minflearn.vo.lecture.QOriginFilename originFilename;

    public final com.innovation.minflearn.vo.lecture.QStoredFilename storedFilename;

    public QLectureFileEntity(String variable) {
        this(LectureFileEntity.class, forVariable(variable), INITS);
    }

    public QLectureFileEntity(Path<? extends LectureFileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureFileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureFileEntity(PathMetadata metadata, PathInits inits) {
        this(LectureFileEntity.class, metadata, inits);
    }

    public QLectureFileEntity(Class<? extends LectureFileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.originFilename = inits.isInitialized("originFilename") ? new com.innovation.minflearn.vo.lecture.QOriginFilename(forProperty("originFilename")) : null;
        this.storedFilename = inits.isInitialized("storedFilename") ? new com.innovation.minflearn.vo.lecture.QStoredFilename(forProperty("storedFilename")) : null;
    }

}

