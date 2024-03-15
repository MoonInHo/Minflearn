package com.innovation.minflearn.course.domain.entity;

import com.innovation.minflearn.course.domain.vo.lecture.LectureDuration;
import com.innovation.minflearn.course.domain.vo.lecture.LectureTitle;
import com.innovation.minflearn.course.domain.vo.lecture.UnitId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private LectureTitle lectureTitle;

    @Embedded
    @Column(nullable = false)
    private LectureDuration lectureDuration;

    @Embedded
    @Column(nullable = false)
    private UnitId unitId;

    @Column(nullable = false)
    private Long sectionId;

    private Lecture(
            LectureTitle lectureTitle,
            LectureDuration lectureDuration,
            UnitId unitId,
            Long sectionId
    ) {
        this.lectureTitle = lectureTitle;
        this.lectureDuration = lectureDuration;
        this.unitId = unitId;
        this.sectionId = sectionId;
    }

    public static Lecture createLecture(
            LectureTitle lectureTitle,
            LectureDuration lectureDuration,
            UnitId unitId,
            Long sectionId
    ) {
        return new Lecture(lectureTitle, lectureDuration, unitId, sectionId);
    }

    public Long id() {
        return id;
    }
}
