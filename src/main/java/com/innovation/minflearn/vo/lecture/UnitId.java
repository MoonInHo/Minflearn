package com.innovation.minflearn.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UnitId {

    private final String unitId;

    private UnitId(String unitId) {
        this.unitId = unitId;
    }

    public static UnitId of(Long courseId, Long sectionId) {

        if (courseId == null) {
            throw new IllegalArgumentException("강좌 번호를 입력하세요.");
        }
        if (sectionId == null) {
            throw new IllegalArgumentException("섹션 번호를 입력하세요.");
        }
        return new UnitId(courseId + "-" + sectionId);
    }
}
