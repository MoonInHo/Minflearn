package com.innovation.minflearn.vo.lecture;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOriginFilename is a Querydsl query type for OriginFilename
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOriginFilename extends BeanPath<OriginFilename> {

    private static final long serialVersionUID = -1863725384L;

    public static final QOriginFilename originFilename1 = new QOriginFilename("originFilename1");

    public final StringPath originFilename = createString("originFilename");

    public QOriginFilename(String variable) {
        super(OriginFilename.class, forVariable(variable));
    }

    public QOriginFilename(Path<? extends OriginFilename> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOriginFilename(PathMetadata metadata) {
        super(OriginFilename.class, metadata);
    }

}

