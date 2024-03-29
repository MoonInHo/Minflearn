package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_file_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private OriginFilename originFilename;

    @Embedded
    @Column(nullable = false)
    private StoredFilename storedFilename;

    @Column(nullable = false)
    private Long lectureId;

    private LectureFileEntity(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long lectureId
    ) {
        this.originFilename = originFilename;
        this.storedFilename = storedFilename;
        this.lectureId = lectureId;
    }

    public static LectureFileEntity createLectureFile(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long lectureId
    ) {
        return new LectureFileEntity(originFilename, storedFilename, lectureId);
    }
}
