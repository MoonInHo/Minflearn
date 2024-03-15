package com.innovation.minflearn.course.domain.vo.course;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Price {

    private final Integer price;

    private Price(Integer price) {
        this.price = price;
    }

    public static Price of(Integer price) {

        if (price == null) {
            throw new IllegalArgumentException("금액을 입력하세요.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("0보다 큰 금액을 입력해주세요");
        }
        return new Price(price);
    }
}
