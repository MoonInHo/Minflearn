package com.innovation.minflearn.course.domain.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OriginFilename {

    private final String originFilename;

    private OriginFilename(String originFilename) {
        this.originFilename = originFilename;
    }

    public static OriginFilename of(String originFilename) {

        if (originFilename == null || originFilename.isBlank()) {
            throw new IllegalArgumentException("원본 파일명을 입력하세요.");
        }
        return new OriginFilename(originFilename);
    }
}
