package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.vo.lecture.LectureTitle;

public record AddLectureRequestDto(
        String lectureTitle
) {
    public LectureEntity toEntity(Long sectionId) {
        return LectureEntity.createLecture(
                LectureTitle.of(lectureTitle),
                sectionId
        );
    }
}
