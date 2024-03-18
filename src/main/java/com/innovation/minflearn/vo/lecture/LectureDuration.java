package com.innovation.minflearn.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LectureDuration {

    private final Integer lectureDuration;

    private LectureDuration(Integer lectureDuration) {
        this.lectureDuration = lectureDuration;
    }

    public static LectureDuration of(Integer lectureDuration) {

        if (lectureDuration == null) {
            throw new IllegalArgumentException("강의 시간을 입력하세요.");
        }
        return new LectureDuration(lectureDuration);
    }
}
