package com.innovation.minflearn.course.domain.enums;

import java.util.Arrays;

public enum Field { // 분야

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String fieldName;

    Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public static Field checkField(String fieldName) {
        return Arrays.stream(Field.values())
                .filter(fd -> fd.fieldName.equals(fieldName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
