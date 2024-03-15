package com.innovation.minflearn.course.domain.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CourseTitle {

    private final String courseTitle;

    private CourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public static CourseTitle of(String courseTitle) {

        if (courseTitle == null || courseTitle.isBlank()) {
            throw new IllegalArgumentException("강좌명을 입력하세요.");
        }
        return new CourseTitle(courseTitle);
    }
}
