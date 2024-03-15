package com.innovation.minflearn.course.application.dto.request;

import com.innovation.minflearn.course.domain.entity.Section;
import com.innovation.minflearn.course.domain.enums.SectionNumber;
import com.innovation.minflearn.course.domain.vo.section.LearningObjective;
import com.innovation.minflearn.course.domain.vo.section.SectionTitle;
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
