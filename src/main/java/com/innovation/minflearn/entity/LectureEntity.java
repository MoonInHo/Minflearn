package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.lecture.LectureTitle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private LectureTitle lectureTitle;

    @Column(nullable = false)
    private Long sectionId;

    @Column(nullable = false)
    private Long memberId;

    private LectureEntity(
            LectureTitle lectureTitle,
            Long sectionId,
            Long memberId
    ) {
        this.lectureTitle = lectureTitle;
        this.sectionId = sectionId;
        this.memberId = memberId;
    }

    public static LectureEntity createLecture(
            LectureTitle lectureTitle,
            Long sectionId,
            Long memberId
    ) {
        return new LectureEntity(lectureTitle, sectionId, memberId);
    }

    public Long id() {
        return id;
    }
}
