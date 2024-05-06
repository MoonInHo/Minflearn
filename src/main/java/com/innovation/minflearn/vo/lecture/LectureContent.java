package com.innovation.minflearn.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LectureContent {

    private final String lectureContent;

    private LectureContent(String lectureContent) {
        this.lectureContent = lectureContent;
    }

    public static LectureContent of(String lectureContent) {
        return new LectureContent(lectureContent);
    }
}
