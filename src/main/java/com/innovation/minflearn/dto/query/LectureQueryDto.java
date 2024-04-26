package com.innovation.minflearn.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LectureQueryDto(
        @JsonIgnore
        Long sectionId,
        Long lectureId,
        String lectureTitle
) {
}
