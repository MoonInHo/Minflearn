package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;
import org.springframework.web.multipart.MultipartFile;

public record AddLectureRequestDto(
        String lectureTitle,
        Integer lectureDuration,
        MultipartFile lectureFile
) {

    public LectureEntity toEntity(Long sectionId, Long memberId) {
        return LectureEntity.createLecture(
                LectureTitle.of(lectureTitle),
                LectureDuration.of(lectureDuration),
                UnitId.of(),
                sectionId,
                memberId
        );
    }
}
