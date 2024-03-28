package com.innovation.minflearn.entity;

import com.innovation.minflearn.vo.course.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity {

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

    private CourseEntity(
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

    public static CourseEntity createCourse(
            CourseTitle courseTitle,
            Description description,
            Category category,
            Instructor instructor,
            Price price,
            CourseSlug courseSlug,
            CourseDuration courseDuration,
            Long memberId
    ) {
        return new CourseEntity(courseTitle, description, category, instructor, price, courseSlug, courseDuration, memberId);
    }

    public Long id() {
        return id;
    }

    public String courseTitle() {
        return courseTitle.courseTitle();
    }

    public String instructor() {
        return instructor.instructor();
    }

    public Integer price() {
        return price.price();
    }
}
