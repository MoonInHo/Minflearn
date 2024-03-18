package com.innovation.minflearn.repository.lecture;

import com.innovation.minflearn.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
