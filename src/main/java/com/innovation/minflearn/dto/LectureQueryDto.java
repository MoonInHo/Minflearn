package com.innovation.minflearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LectureQueryDto(
        @JsonIgnore
        Long sectionId,
        Long lectureId,
        String lectureTitle,
        Integer lectureDuration
) {
}
