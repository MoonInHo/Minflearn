package com.innovation.minflearn.vo.course;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseSlug is a Querydsl query type for CourseSlug
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCourseSlug extends BeanPath<CourseSlug> {

    private static final long serialVersionUID = -2081496194L;

    public static final QCourseSlug courseSlug1 = new QCourseSlug("courseSlug1");

    public final StringPath courseSlug = createString("courseSlug");

    public QCourseSlug(String variable) {
        super(CourseSlug.class, forVariable(variable));
    }

    public QCourseSlug(Path<? extends CourseSlug> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseSlug(PathMetadata metadata) {
        super(CourseSlug.class, metadata);
    }

}

