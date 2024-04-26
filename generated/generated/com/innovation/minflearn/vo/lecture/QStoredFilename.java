package com.innovation.minflearn.vo.lecture;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoredFilename is a Querydsl query type for StoredFilename
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStoredFilename extends BeanPath<StoredFilename> {

    private static final long serialVersionUID = -309168587L;

    public static final QStoredFilename storedFilename1 = new QStoredFilename("storedFilename1");

    public final StringPath storedFilename = createString("storedFilename");

    public QStoredFilename(String variable) {
        super(StoredFilename.class, forVariable(variable));
    }

    public QStoredFilename(Path<? extends StoredFilename> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoredFilename(PathMetadata metadata) {
        super(StoredFilename.class, metadata);
    }

}

