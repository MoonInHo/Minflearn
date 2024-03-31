package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.dto.query.SectionQueryDto;
import com.innovation.minflearn.enums.SectionNumber;

import java.util.List;

public interface SectionQueryRepository {

    boolean isSectionExist(SectionNumber sectionNumber, Long courseId, Long memberId);

    List<SectionQueryDto> getSections(Long courseId);
}
