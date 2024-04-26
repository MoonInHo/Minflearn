package com.innovation.minflearn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseEntity is a Querydsl query type for CourseEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseEntity extends EntityPathBase<CourseEntity> {

    private static final long serialVersionUID = -415155537L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseEntity courseEntity = new QCourseEntity("courseEntity");

    public final com.innovation.minflearn.vo.course.QCategory category;

    public final com.innovation.minflearn.vo.course.QCourseDuration courseDuration;

    public final com.innovation.minflearn.vo.course.QCourseSlug courseSlug;

    public final com.innovation.minflearn.vo.course.QCourseTitle courseTitle;

    public final com.innovation.minflearn.vo.course.QDescription description;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.innovation.minflearn.vo.course.QInstructor instructor;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final com.innovation.minflearn.vo.course.QPrice price;

    public QCourseEntity(String variable) {
        this(CourseEntity.class, forVariable(variable), INITS);
    }

    public QCourseEntity(Path<? extends CourseEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseEntity(PathMetadata metadata, PathInits inits) {
        this(CourseEntity.class, metadata, inits);
    }

    public QCourseEntity(Class<? extends CourseEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.innovation.minflearn.vo.course.QCategory(forProperty("category")) : null;
        this.courseDuration = inits.isInitialized("courseDuration") ? new com.innovation.minflearn.vo.course.QCourseDuration(forProperty("courseDuration")) : null;
        this.courseSlug = inits.isInitialized("courseSlug") ? new com.innovation.minflearn.vo.course.QCourseSlug(forProperty("courseSlug")) : null;
        this.courseTitle = inits.isInitialized("courseTitle") ? new com.innovation.minflearn.vo.course.QCourseTitle(forProperty("courseTitle")) : null;
        this.description = inits.isInitialized("description") ? new com.innovation.minflearn.vo.course.QDescription(forProperty("description")) : null;
        this.instructor = inits.isInitialized("instructor") ? new com.innovation.minflearn.vo.course.QInstructor(forProperty("instructor")) : null;
        this.price = inits.isInitialized("price") ? new com.innovation.minflearn.vo.course.QPrice(forProperty("price")) : null;
    }

}

