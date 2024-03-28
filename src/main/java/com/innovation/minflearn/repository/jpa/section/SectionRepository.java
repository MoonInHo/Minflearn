package com.innovation.minflearn.repository.jpa.section;

import com.innovation.minflearn.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<SectionEntity, Long>, SectionQueryRepository {
}
