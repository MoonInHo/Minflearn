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

//    @Embedded
//    @Column(nullable = false)
//    private LectureDuration lectureDuration;

    @Column(nullable = false)
    private Long courseId;

    private LectureFileEntity(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long courseId
    ) {
        this.originFilename = originFilename;
        this.storedFilename = storedFilename;
        this.courseId = courseId;
    }

    public static LectureFileEntity createLectureFile(
            OriginFilename originFilename,
            StoredFilename storedFilename,
            Long courseId
    ) {
        return new LectureFileEntity(originFilename, storedFilename, courseId);
    }

    public Long id() {
        return id;
    }
}
