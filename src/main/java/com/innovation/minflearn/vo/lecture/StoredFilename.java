package com.innovation.minflearn.vo.lecture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class StoredFilename {

    private final String storedFilename;

    private StoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

    public static StoredFilename of(String storedFilename) {

        if (storedFilename == null || storedFilename.isBlank()) {
            throw new IllegalArgumentException("서버 저장용 파일명을 입력하세요.");
        }
        return new StoredFilename(storedFilename);
    }
}
