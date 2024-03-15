package com.innovation.minflearn.course.domain.repository;

import com.innovation.minflearn.course.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
