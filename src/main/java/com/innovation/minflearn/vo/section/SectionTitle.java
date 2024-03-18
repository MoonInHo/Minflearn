package com.innovation.minflearn.vo.section;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SectionTitle {

    private final String sectionTitle;

    private SectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public static SectionTitle of(String sectionTitle) {
        return new SectionTitle(sectionTitle);
    }
}
