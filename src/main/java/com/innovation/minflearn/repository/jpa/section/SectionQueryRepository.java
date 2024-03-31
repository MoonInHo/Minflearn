package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.dto.query.SectionQueryDto;
import com.innovation.minflearn.enums.SectionNumber;

import java.util.List;

public interface SectionQueryRepository {

    SectionNumber getLastSectionNumber(Long courseId);

    boolean isSectionExist(Long sectionId);

    List<SectionQueryDto> getLecturesBySections(Long courseId);
}
