package com.innovation.minflearn.course.domain.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CourseSlug {

    private final String courseSlug;

    private CourseSlug(String courseSlug) {
        this.courseSlug = courseSlug;
    }

    public static CourseSlug of(String courseSlug) {

        if (courseSlug == null || courseSlug.isBlank()) {
            throw new IllegalArgumentException("강좌 식별값을 입력하세요.");
        }
        if (!courseSlug.matches("^[a-z0-9-]+$")) {
            throw new IllegalArgumentException("강좌 식별값 형식이 올바르지 않습니다.");
        }
        return new CourseSlug(courseSlug);
    }
}
