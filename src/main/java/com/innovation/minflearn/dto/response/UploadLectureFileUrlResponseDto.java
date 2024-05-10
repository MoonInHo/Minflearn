package com.innovation.minflearn.dto.response;

public record UploadLectureFileUrlResponseDto(
        String uploadUrl
) {
    public static UploadLectureFileUrlResponseDto generateUploadUrl(
            Long courseId,
            Long lectureFileId
    ) {
        return new UploadLectureFileUrlResponseDto(
                "/api/courses/" + courseId + "/lecture-files/" + lectureFileId + "/chunks"
        );
    }
}