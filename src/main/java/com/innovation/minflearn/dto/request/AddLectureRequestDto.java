package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;

public record AddLectureRequestDto(
        Long sectionId,
        String lectureTitle,
        Integer lectureDuration,
        int chunkNumber,
        int totalChunks
) {

    public LectureEntity toEntity(Long memberId) {
        return LectureEntity.createLecture(
                LectureTitle.of(lectureTitle),
                LectureDuration.of(lectureDuration),
                UnitId.of(),
                sectionId,
                memberId
        );
    }
}
