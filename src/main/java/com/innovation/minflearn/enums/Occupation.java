package com.innovation.minflearn.enums;

import java.util.Arrays;

public enum Occupation { // 직군

    DEVELOPMENT_PROGRAMING("개발-프로그래밍"),
    DESIGN("디자인");

    private final String occupationName;

    Occupation(String occupationName) {
        this.occupationName = occupationName;
    }

    public static Occupation checkOccupation(String occupation) {
        return Arrays.stream(Occupation.values())
                .filter(oc -> oc.occupationName.equals(occupation))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
