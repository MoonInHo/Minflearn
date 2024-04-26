package com.innovation.minflearn.vo.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBirthDate is a Querydsl query type for BirthDate
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBirthDate extends BeanPath<BirthDate> {

    private static final long serialVersionUID = -1944687692L;

    public static final QBirthDate birthDate1 = new QBirthDate("birthDate1");

    public final StringPath birthDate = createString("birthDate");

    public QBirthDate(String variable) {
        super(BirthDate.class, forVariable(variable));
    }

    public QBirthDate(Path<? extends BirthDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBirthDate(PathMetadata metadata) {
        super(BirthDate.class, metadata);
    }

}

