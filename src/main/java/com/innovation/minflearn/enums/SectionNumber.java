package com.innovation.minflearn.enums;

import com.innovation.minflearn.exception.custom.section.SectionLimitExceededException;

import java.util.Arrays;

public enum SectionNumber {

    SECTION_1, SECTION_2,
    SECTION_3, SECTION_4,
    SECTION_5, SECTION_6,
    SECTION_7, SECTION_8,
    SECTION_9, SECTION_10,
    SECTION_11, SECTION_12,
    SECTION_13, SECTION_14,
    SECTION_15, SECTION_16,
    SECTION_17, SECTION_18,
    SECTION_19, SECTION_20;

    public static SectionNumber getNextSection(SectionNumber sectionNumber) {
        if (sectionNumber == null) {
            return SECTION_1;
        }
        return Arrays.stream(SectionNumber.values())
                .filter(sn -> sn.ordinal() == sectionNumber.ordinal() + 1)
                .findFirst()
                .orElseThrow(SectionLimitExceededException::new);
    }
}
