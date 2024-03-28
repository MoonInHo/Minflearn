package com.innovation.minflearn.repository.jpa.cource;

import com.innovation.minflearn.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>, CourseQueryRepository {
}
