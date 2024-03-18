package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.course.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private CourseTitle courseTitle;

    @Embedded
    private Description description;

    @Embedded
    @Column(nullable = false)
    private Category category;

    @Embedded
    @Column(nullable = false)
    private Instructor instructor;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @Embedded
    @Column(nullable = false)
    private CourseSlug courseSlug;

    @Embedded
    @Column(nullable = false)
    private CourseDuration courseDuration;

    @Column(nullable = false)
    private Long memberId;

    private Course(
            CourseTitle courseTitle,
            Description description,
            Category category,
            Instructor instructor,
            Price price,
            CourseSlug courseSlug,
            CourseDuration courseDuration,
            Long memberId
    ) {
        this.courseTitle = courseTitle;
        this.description = description;
        this.category = category;
        this.instructor = instructor;
        this.price = price;
        this.courseSlug = courseSlug;
        this.courseDuration = courseDuration;
        this.memberId = memberId;
    }

    public static Course createCourse(
            CourseTitle courseTitle,
            Description description,
            Category category,
            Instructor instructor,
            Price price,
            CourseSlug courseSlug,
            CourseDuration courseDuration,
            Long memberId
    ) {
        return new Course(courseTitle, description, category, instructor, price, courseSlug, courseDuration, memberId);
    }
}
