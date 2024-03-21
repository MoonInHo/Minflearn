package com.innovation.minflearn.repository.cource;

import com.innovation.minflearn.entity.CourseEntity;
import com.innovation.minflearn.repository.section.SectionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>, CourseQueryRepository {
}
