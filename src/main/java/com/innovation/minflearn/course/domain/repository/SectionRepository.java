package com.innovation.minflearn.course.domain.repository;

import com.innovation.minflearn.course.domain.entity.Section;
import com.innovation.minflearn.course.infrastructure.repository.SectionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long>, SectionQueryRepository {
}
