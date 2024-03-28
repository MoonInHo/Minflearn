package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
}
