package com.innovation.minflearn.vo.lecture;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLectureTitle is a Querydsl query type for LectureTitle
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLectureTitle extends BeanPath<LectureTitle> {

    private static final long serialVersionUID = 698003877L;

    public static final QLectureTitle lectureTitle1 = new QLectureTitle("lectureTitle1");

    public final StringPath lectureTitle = createString("lectureTitle");

    public QLectureTitle(String variable) {
        super(LectureTitle.class, forVariable(variable));
    }

    public QLectureTitle(Path<? extends LectureTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLectureTitle(PathMetadata metadata) {
        super(LectureTitle.class, metadata);
    }

}

