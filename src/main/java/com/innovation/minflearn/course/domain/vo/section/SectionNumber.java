package com.innovation.minflearn.course.domain.vo.section;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SectionNumber {

    private final String sectionNumber; //TODO (섹션 추가시 번호 순으로 자동 생성 되도록 변경 예정)

    private SectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public static SectionNumber of(String sectionNumber) {
        return new SectionNumber(sectionNumber);
    }
}
