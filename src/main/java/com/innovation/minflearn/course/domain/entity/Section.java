package com.innovation.minflearn.course.domain.entity;

import com.innovation.minflearn.course.domain.vo.section.LearningObjective;
import com.innovation.minflearn.course.domain.vo.section.SectionTitle;
import com.innovation.minflearn.course.domain.vo.section.SectionNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private SectionNumber sectionNumber;

    @Embedded
    private SectionTitle sectionTitle;

    @Embedded
    private LearningObjective learningObjective;

    private Long courseId;
}
