package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.enums.SectionNumber;

public interface SectionQueryRepository {

    SectionNumber getLastSectionNumber(Long courseId);

    boolean isSectionExist(Long courseId, Long sectionId);
}
