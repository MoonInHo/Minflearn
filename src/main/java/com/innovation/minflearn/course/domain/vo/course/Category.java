package com.innovation.minflearn.course.domain.vo.course;

import com.innovation.minflearn.course.domain.enums.Field;
import com.innovation.minflearn.course.domain.enums.Occupation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Category {

    @Enumerated(EnumType.STRING)
    private final Occupation occupation;
    @Enumerated(EnumType.STRING)
    private final Field field;
    private final List<String> skillTag;

    private Category(Occupation occupation, Field field, List<String> skillTag) {
        this.occupation = occupation;
        this.field = field;
        this.skillTag = skillTag;
    }

    public static Category of(Occupation occupation, Field field, List<String> skillTag) {

        if (skillTag.isEmpty()) {
            throw new IllegalArgumentException("기술 스택을 입력하세요.");
        }
        if (skillTag.size() > 5) {
            throw new IllegalArgumentException("강의 스킬태그는 최대 5개 까지 선택 가능합니다.");
        }
        return new Category(occupation, field, skillTag);
    }
}
