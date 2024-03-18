package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.Course;
import com.innovation.minflearn.enums.Field;
import com.innovation.minflearn.enums.Occupation;
import com.innovation.minflearn.vo.course.*;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateCourseRequestDto {

    private String courseTitle;
    private String description;
    private String occupation;
    private String field;
    private List<String> skillTag;
    private String instructor;
    private Integer price;
    private String courseSlug;
    private Integer courseDuration;

    public Course toEntity(Long memberId) {
        return Course.createCourse(
                CourseTitle.of(courseTitle),
                Description.of(description),
                Category.of(
                        Occupation.checkOccupation(occupation),
                        Field.checkField(field),
                        skillTag
                ),
                Instructor.of(instructor),
                Price.of(price),
                CourseSlug.of(courseSlug),
                CourseDuration.of(courseDuration),
                memberId
        );
    }
}
