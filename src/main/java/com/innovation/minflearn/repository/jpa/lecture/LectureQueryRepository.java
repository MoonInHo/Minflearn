package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureEntity;

import java.util.Optional;

public interface LectureQueryRepository {

    Optional<LectureEntity> getLectureEntity(Long lectureId);
}
