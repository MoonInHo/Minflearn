package com.innovation.minflearn.course.domain.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LectureTitle {

    private final String lectureTitle;

    private LectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public static LectureTitle of(String lectureTitle) {

        if (lectureTitle == null || lectureTitle.isBlank()) {
            throw new IllegalArgumentException("강의 제목을 입력하세요.");
        }
        return new LectureTitle(lectureTitle);
    }
}
