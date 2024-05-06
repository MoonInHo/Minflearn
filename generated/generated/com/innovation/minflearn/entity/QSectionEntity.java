package com.innovation.minflearn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSectionEntity is a Querydsl query type for SectionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSectionEntity extends EntityPathBase<SectionEntity> {

    private static final long serialVersionUID = -2091065737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSectionEntity sectionEntity = new QSectionEntity("sectionEntity");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.innovation.minflearn.vo.section.QLearningObjective learningObjective;

    public final EnumPath<com.innovation.minflearn.enums.SectionNumber> sectionNumber = createEnum("sectionNumber", com.innovation.minflearn.enums.SectionNumber.class);

    public final com.innovation.minflearn.vo.section.QSectionTitle sectionTitle;

    public QSectionEntity(String variable) {
        this(SectionEntity.class, forVariable(variable), INITS);
    }

    public QSectionEntity(Path<? extends SectionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSectionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSectionEntity(PathMetadata metadata, PathInits inits) {
        this(SectionEntity.class, metadata, inits);
    }

    public QSectionEntity(Class<? extends SectionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.learningObjective = inits.isInitialized("learningObjective") ? new com.innovation.minflearn.vo.section.QLearningObjective(forProperty("learningObjective")) : null;
        this.sectionTitle = inits.isInitialized("sectionTitle") ? new com.innovation.minflearn.vo.section.QSectionTitle(forProperty("sectionTitle")) : null;
    }

}

