package com.innovation.minflearn.repository.section;

import com.innovation.minflearn.enums.SectionNumber;

public interface SectionQueryRepository {

    boolean isSectionExist(Long courseId, SectionNumber sectionNumber);
}
