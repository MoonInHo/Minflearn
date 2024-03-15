package com.innovation.minflearn.course.domain.vo.section;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LearningObjective {

    private final String learningObjective;

    private LearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
    }

    public static LearningObjective of(String learningObjective) {
        return new LearningObjective(learningObjective);
    }
}
