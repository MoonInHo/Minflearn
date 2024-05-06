package com.innovation.minflearn.dto.request;

public record EditLectureRequestDto(
        Long lectureFileId,
        String lectureContent
) {
}
