package com.innovation.minflearn.course.domain.repository;

import com.innovation.minflearn.course.domain.entity.LectureFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureFileRepository extends JpaRepository<LectureFile, Long> {
}
