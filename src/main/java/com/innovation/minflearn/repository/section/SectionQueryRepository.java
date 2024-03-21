package com.innovation.minflearn.repository.section;

import com.innovation.minflearn.dto.SectionDto;
import com.innovation.minflearn.enums.SectionNumber;

import java.util.List;

public interface SectionQueryRepository {

    boolean isSectionExist(SectionNumber sectionNumber, Long courseId, Long memberId);

    List<SectionDto> getSections(Long courseId);
}
