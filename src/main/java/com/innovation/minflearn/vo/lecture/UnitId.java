package com.innovation.minflearn.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UnitId {

    private final Integer unitId;

    private UnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public static UnitId of() {
        //TODO unitID 생성 조건 설정하기 (ex. 강좌번호 + 섹션번호 + 강의번호)
        return new UnitId(0);
    }
}
