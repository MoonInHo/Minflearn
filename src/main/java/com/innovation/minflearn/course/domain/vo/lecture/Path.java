package com.innovation.minflearn.course.domain.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Path {

    private final String path;

    private Path(String path) {
        this.path = path;
    }

    public static Path of(String path) {

        //TODO FileUpload 에 대해 알아보기
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("파일 경로를 입력하세요.");
        }
        return new Path(path);
    }
}
