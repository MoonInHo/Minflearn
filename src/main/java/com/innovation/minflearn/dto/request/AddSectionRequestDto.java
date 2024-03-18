package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.Section;
import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.vo.section.LearningObjective;
import com.innovation.minflearn.vo.section.SectionTitle;
import lombok.Getter;

@Getter
public class AddSectionRequestDto {

    private String sectionNumber;
    private String sectionTitle;
    private String learningObjective;

    public Section toEntity(Long courseId, SectionNumber sectionNumber) {
        return Section.createSection(
                sectionNumber,
                SectionTitle.of(sectionTitle),
                LearningObjective.of(learningObjective),
                courseId
        );
    }
}
