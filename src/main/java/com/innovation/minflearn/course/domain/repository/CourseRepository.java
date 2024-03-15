package com.innovation.minflearn.course.domain.repository;

import com.innovation.minflearn.course.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
