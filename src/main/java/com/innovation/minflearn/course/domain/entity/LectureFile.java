package com.innovation.minflearn.course.domain.entity;

import com.innovation.minflearn.course.domain.vo.lecture.OriginFilename;
import com.innovation.minflearn.course.domain.vo.lecture.StoredFilename;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private OriginFilename originFilename;

    @Embedded
    @Column(nullable = false)
    private StoredFilename storedFilename;

    @Column(nullable = false)
    private Long lectureId;

    private LectureFile(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long lectureId
    ) {
        this.originFilename = originFilename;
        this.storedFilename = storedFilename;
        this.lectureId = lectureId;
    }

    public static LectureFile createLectureFile(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long lectureId
    ) {
        return new LectureFile(originFilename, storedFilename, lectureId);
    }
}
