package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.SectionEntity;
import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.vo.section.LearningObjective;
import com.innovation.minflearn.vo.section.SectionTitle;

public record AddSectionRequestDto(
        String sectionTitle,
        String learningObjective
) {
    public SectionEntity toEntity(SectionNumber sectionNumber, Long courseId, Long memberId) {
        return SectionEntity.createSection(
                sectionNumber,
                SectionTitle.of(sectionTitle),
                LearningObjective.of(learningObjective),
                courseId,
                memberId
        );
    }
}
