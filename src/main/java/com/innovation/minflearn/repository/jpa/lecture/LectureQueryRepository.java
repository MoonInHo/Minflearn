package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureEntity;

public interface LectureQueryRepository {

    LectureEntity getLectureEntity(Long lectureId);

    boolean isLectureExist(Long sectionId, Long lectureId);
}
