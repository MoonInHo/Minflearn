package com.innovation.minflearn.vo.section;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLearningObjective is a Querydsl query type for LearningObjective
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLearningObjective extends BeanPath<LearningObjective> {

    private static final long serialVersionUID = -830086633L;

    public static final QLearningObjective learningObjective1 = new QLearningObjective("learningObjective1");

    public final StringPath learningObjective = createString("learningObjective");

    public QLearningObjective(String variable) {
        super(LearningObjective.class, forVariable(variable));
    }

    public QLearningObjective(Path<? extends LearningObjective> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLearningObjective(PathMetadata metadata) {
        super(LearningObjective.class, metadata);
    }

}

