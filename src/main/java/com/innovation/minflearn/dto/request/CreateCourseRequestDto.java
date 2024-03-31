package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.CourseEntity;
import com.innovation.minflearn.enums.Field;
import com.innovation.minflearn.enums.Occupation;
import com.innovation.minflearn.vo.course.*;

import java.util.List;

public record CreateCourseRequestDto(
        String courseTitle,
        String description,
        String occupation,
        String field,
        List<String> skillTag,
        String instructor,
        Integer price,
        String courseSlug,
        Integer courseDuration
) {
    public CourseEntity toEntity(Long memberId) {
        return CourseEntity.createCourse(
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
