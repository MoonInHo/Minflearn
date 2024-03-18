package com.innovation.minflearn.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Description {

    private final String description;

    private Description(String description) {
        this.description = description;
    }

    public static Description of(String description) {
        return new Description(description);
    }
}
