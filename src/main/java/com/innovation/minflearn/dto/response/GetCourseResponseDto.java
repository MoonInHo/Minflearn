package com.innovation.minflearn.dto.response;

public record GetCourseResponseDto(
        String courseTitle,
        String instructor,
        Integer price
) {
}
