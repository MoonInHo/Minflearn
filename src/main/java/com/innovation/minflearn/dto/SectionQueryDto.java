package com.innovation.minflearn.dto;

import com.innovation.minflearn.enums.SectionNumber;
import lombok.Getter;

import java.util.List;

@Getter
public class SectionQueryDto {

    private Long sectionId;
    private SectionNumber sectionNumber;
    private String sectionTitle;
    private String learningObjective;
    private List<LectureQueryDto> lectures;

    public void includeLectures(List<LectureQueryDto> lectures) {
        this.lectures = lectures;
    }
}
