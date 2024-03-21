package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureEntity {

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

    @Column(nullable = false)
    private Long memberId;

    private LectureEntity(
            LectureTitle lectureTitle,
            LectureDuration lectureDuration,
            UnitId unitId,
            Long sectionId,
            Long memberId
    ) {
        this.lectureTitle = lectureTitle;
        this.lectureDuration = lectureDuration;
        this.unitId = unitId;
        this.sectionId = sectionId;
        this.memberId = memberId;
    }

    public static LectureEntity createLecture(
            LectureTitle lectureTitle,
            LectureDuration lectureDuration,
            UnitId unitId,
            Long sectionId,
            Long memberId
    ) {
        return new LectureEntity(lectureTitle, lectureDuration, unitId, sectionId, memberId);
    }

    public Long id() {
        return id;
    }
}
