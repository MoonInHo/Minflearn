package com.innovation.minflearn.vo.course;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCategory extends BeanPath<Category> {

    private static final long serialVersionUID = 237931798L;

    public static final QCategory category = new QCategory("category");

    public final EnumPath<com.innovation.minflearn.enums.Field> field = createEnum("field", com.innovation.minflearn.enums.Field.class);

    public final EnumPath<com.innovation.minflearn.enums.Occupation> occupation = createEnum("occupation", com.innovation.minflearn.enums.Occupation.class);

    public final ListPath<String, StringPath> skillTag = this.<String, StringPath>createList("skillTag", String.class, StringPath.class, PathInits.DIRECT2);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

