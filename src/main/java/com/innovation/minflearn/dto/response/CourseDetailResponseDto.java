package com.innovation.minflearn.dto.response;

import com.innovation.minflearn.dto.SectionDto;
import com.innovation.minflearn.enums.Field;
import com.innovation.minflearn.enums.Occupation;
import lombok.Getter;

import java.util.List;

@Getter
public class CourseDetailResponseDto {

    private String courseTitle;
    private String description;
    private Occupation occupation;
    private Field field;
    private List<String> skillTag;
    private String instructor;
    private Integer price;
    private Integer courseDuration;
    private List<SectionDto> sections;

    public void includeSections(List<SectionDto> sections) {
        this.sections = sections;
    }
}
