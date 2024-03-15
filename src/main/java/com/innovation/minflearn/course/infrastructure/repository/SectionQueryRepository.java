package com.innovation.minflearn.course.infrastructure.repository;

import com.innovation.minflearn.course.domain.enums.SectionNumber;

public interface SectionQueryRepository {

    boolean isSectionExist(Long courseId, SectionNumber sectionNumber);
}
