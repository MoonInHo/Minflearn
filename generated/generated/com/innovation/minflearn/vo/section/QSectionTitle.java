package com.innovation.minflearn.vo.section;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSectionTitle is a Querydsl query type for SectionTitle
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSectionTitle extends BeanPath<SectionTitle> {

    private static final long serialVersionUID = -1755228521L;

    public static final QSectionTitle sectionTitle1 = new QSectionTitle("sectionTitle1");

    public final StringPath sectionTitle = createString("sectionTitle");

    public QSectionTitle(String variable) {
        super(SectionTitle.class, forVariable(variable));
    }

    public QSectionTitle(Path<? extends SectionTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSectionTitle(PathMetadata metadata) {
        super(SectionTitle.class, metadata);
    }

}

