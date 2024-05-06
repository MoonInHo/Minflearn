package com.innovation.minflearn.entity;

import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.vo.section.LearningObjective;
import com.innovation.minflearn.vo.section.SectionTitle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionNumber sectionNumber;

    @Embedded
    private SectionTitle sectionTitle;

    @Embedded
    private LearningObjective learningObjective;

    @Column(nullable = false)
    private Long courseId;

    private SectionEntity(
            SectionNumber sectionNumber,
            SectionTitle sectionTitle,
            LearningObjective learningObjective,
            Long courseId
    ) {
        this.sectionNumber = sectionNumber;
        this.sectionTitle = sectionTitle;
        this.learningObjective = learningObjective;
        this.courseId = courseId;
    }

    public static SectionEntity createSection(
            SectionNumber sectionNumber,
            SectionTitle sectionTitle,
            LearningObjective learningObjective,
            Long courseId
    ) {
        return new SectionEntity(sectionNumber, sectionTitle, learningObjective, courseId);
    }
}
