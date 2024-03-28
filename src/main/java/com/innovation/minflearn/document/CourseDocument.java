package com.innovation.minflearn.document;

import com.innovation.minflearn.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "idx_es_course")
@Setting(settingPath = "static/setting.json") //TODO tokenizer {lowercase, snowball} 추가 예정
@Mapping(mappingPath = "static/mapping.json")
public class CourseDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String courseTitle;

    @Field(type = FieldType.Text)
    private String instructor;

    @Field(type = FieldType.Integer)
    private Integer price;

    public static CourseDocument of(CourseEntity courseEntity) {
        return new CourseDocument(
                courseEntity.id(),
                courseEntity.courseTitle(),
                courseEntity.instructor(),
                courseEntity.price()
        );
    }
}

