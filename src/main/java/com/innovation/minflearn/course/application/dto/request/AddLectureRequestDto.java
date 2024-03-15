package com.innovation.minflearn.course.application.dto.request;

import com.innovation.minflearn.course.domain.entity.Lecture;
import com.innovation.minflearn.course.domain.vo.lecture.LectureDuration;
import com.innovation.minflearn.course.domain.vo.lecture.LectureTitle;
import com.innovation.minflearn.course.domain.vo.lecture.UnitId;
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
