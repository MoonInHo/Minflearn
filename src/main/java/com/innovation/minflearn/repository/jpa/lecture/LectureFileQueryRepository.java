package com.innovation.minflearn.repository.jpa.lecture;

import com.innovation.minflearn.dto.query.LectureFileQueryDto;

public interface LectureFileQueryRepository {

    LectureFileQueryDto getFilename(Long lectureFileId);

    String getOriginFilename(Long lectureFileId);
}
