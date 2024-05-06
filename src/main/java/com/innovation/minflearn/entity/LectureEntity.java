package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.lecture.LectureContent;
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

    @Embedded
    private LectureContent lectureContent;

    @Column(nullable = false)
    private Long sectionId;

    private Long lectureFileId;

    public LectureEntity(
            LectureTitle lectureTitle,
            Long sectionId
    ) {
        this.lectureTitle = lectureTitle;
        this.sectionId = sectionId;
    }

    public static LectureEntity createLecture(
            LectureTitle lectureTitle,
            Long sectionId
    ) {
        return new LectureEntity(lectureTitle, sectionId);
    }

    public void editLectureContent(LectureContent lectureContent, Long lectureFileId) {
        this.lectureContent = lectureContent;
        this.lectureFileId = lectureFileId;
    }

    public Long id() {
        return id;
    }
}
