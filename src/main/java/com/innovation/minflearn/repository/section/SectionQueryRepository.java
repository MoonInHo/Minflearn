package com.innovation.minflearn.repository.section;

import com.innovation.minflearn.enums.SectionNumber;

public interface SectionQueryRepository {

    boolean isSectionExist(SectionNumber sectionNumber, Long courseId, Long memberId);
}
