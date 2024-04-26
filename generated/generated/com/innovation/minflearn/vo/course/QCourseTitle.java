package com.innovation.minflearn.vo.course;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseTitle is a Querydsl query type for CourseTitle
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCourseTitle extends BeanPath<CourseTitle> {

    private static final long serialVersionUID = -101039131L;

    public static final QCourseTitle courseTitle1 = new QCourseTitle("courseTitle1");

    public final StringPath courseTitle = createString("courseTitle");

    public QCourseTitle(String variable) {
        super(CourseTitle.class, forVariable(variable));
    }

    public QCourseTitle(Path<? extends CourseTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseTitle(PathMetadata metadata) {
        super(CourseTitle.class, metadata);
    }

}

