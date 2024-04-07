package com.innovation.minflearn.dto.request;

public record AddLectureRequestDto(
        String lectureTitle,
        Integer lectureDuration
) {
}
