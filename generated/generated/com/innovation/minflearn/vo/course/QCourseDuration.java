package com.innovation.minflearn.vo.course;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseDuration is a Querydsl query type for CourseDuration
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCourseDuration extends BeanPath<CourseDuration> {

    private static final long serialVersionUID = 593405159L;

    public static final QCourseDuration courseDuration1 = new QCourseDuration("courseDuration1");

    public final NumberPath<Integer> courseDuration = createNumber("courseDuration", Integer.class);

    public QCourseDuration(String variable) {
        super(CourseDuration.class, forVariable(variable));
    }

    public QCourseDuration(Path<? extends CourseDuration> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseDuration(PathMetadata metadata) {
        super(CourseDuration.class, metadata);
    }

}

