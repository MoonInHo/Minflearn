package com.innovation.minflearn.course.domain.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Instructor {

    private final String Instructor;

    private Instructor(String instructor) {
        Instructor = instructor;
    }

    public static Instructor of(String instructor) {

        if (instructor == null || instructor.isBlank()) {
            throw new IllegalArgumentException("강사명을 입력하세요.");
        }
        return new Instructor(instructor);
    }
}
