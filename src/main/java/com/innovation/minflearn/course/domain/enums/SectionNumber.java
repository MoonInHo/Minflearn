package com.innovation.minflearn.course.domain.enums;

import com.innovation.minflearn.course.domain.entity.Section;

import java.util.Arrays;

public enum SectionNumber {

    SECTION_ONE("섹션 1"),
    SECTION_TWO("섹션 2"),
    SECTION_THREE("섹션 3"),
    SECTION_FORE("섹션 4"),
    SECTION_FIVE("섹션 5"),
    SECTION_SIX("섹션 6"),
    SECTION_SEVEN("섹션 7"),
    SECTION_EIGHT("섹션 8"),
    SECTION_NINE("섹션 9"),
    SECTION_TEN("섹션 10");

    private final String sectionNumber;

    SectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public static SectionNumber checkSection(String sectionNumber) {
        return Arrays.stream(SectionNumber.values())
                .filter(sec -> sec.sectionNumber.equals(sectionNumber))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
