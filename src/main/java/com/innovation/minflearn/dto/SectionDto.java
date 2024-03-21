package com.innovation.minflearn.dto;

import com.innovation.minflearn.enums.SectionNumber;
import lombok.Getter;

import java.util.List;

@Getter
public class SectionDto {

    private Long id;
    private SectionNumber sectionNumber;
    private String sectionTitle;
    private String learningObjective;
    private List<LectureDto> lectures;

    public void includeLectures(List<LectureDto> lectures) {
        this.lectures = lectures;
    }
}
