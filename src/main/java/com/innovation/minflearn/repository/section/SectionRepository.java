package com.innovation.minflearn.repository.section;

import com.innovation.minflearn.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long>, SectionQueryRepository {
}
