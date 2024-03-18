package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;
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

    //TODO 연관 관계 매핑이 안되어 있으므로 권한 체크를 위해 memberId 를 넣는게 적절한 설계인지 고민

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
