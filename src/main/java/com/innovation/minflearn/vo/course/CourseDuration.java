package com.innovation.minflearn.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CourseDuration {

    private final Integer courseDuration;

    private CourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public static CourseDuration of(Integer courseDuration) {

        if (courseDuration == null) {
            throw new IllegalArgumentException("강좌의 총 시간을 입력하세요.");
        }
        return new CourseDuration(courseDuration);
    }
}
