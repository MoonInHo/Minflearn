package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.Lecture;
import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;
import lombok.Getter;

@Getter
public class AddLectureRequestDto {

    private String lectureTitle;
    private Integer lectureDuration;

    public Lecture toEntity(Long courseId) {
        return Lecture.createLecture(
                LectureTitle.of(lectureTitle),
                LectureDuration.of(lectureDuration),
                UnitId.of(),
                courseId
        );
    }
}
