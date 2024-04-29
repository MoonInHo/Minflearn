package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.dto.LectureFileQueryDto;

public interface LectureFileQueryRepository {

    LectureFileQueryDto getFilename(Long lectureFileId);
}
