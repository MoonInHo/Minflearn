package com.innovation.minflearn.entity;

import com.innovation.minflearn.enums.SectionNumber;
import com.innovation.minflearn.vo.section.LearningObjective;
import com.innovation.minflearn.vo.section.SectionTitle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //TODO 권한 체크를 할 방법에 대해 고민

    private Section(
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

    public static Section createSection(
            SectionNumber sectionNumber,
            SectionTitle sectionTitle,
            LearningObjective learningObjective,
            Long courseId
    ) {
        return new Section(sectionNumber, sectionTitle, learningObjective, courseId);
    }
}
