package com.innovation.minflearn.vo.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class BirthDate {

    private final String birthDate;

    private BirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public static BirthDate of(String birthDate) {

        if (birthDate == null || birthDate.isBlank()) {
            return new BirthDate(null);
        }
        if (!birthDate.matches("^(19|20)\\d{2}\\.(0[1-9]|1[0-2])\\.(0[1-9]|[12][0-9]|3[01])$")) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }

        LocalDate birthdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if (birthdate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("생일은 현재 날짜 이후로 설정할 수 없습니다.");
        }
        return new BirthDate(birthDate);
    }
}
