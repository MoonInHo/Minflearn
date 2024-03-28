package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.entity.LectureFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureFileRepository extends JpaRepository<LectureFileEntity, Long> {
}
