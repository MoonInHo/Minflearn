package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.vo.lecture.LectureDuration;
import com.innovation.minflearn.vo.lecture.LectureTitle;
import com.innovation.minflearn.vo.lecture.UnitId;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddLectureRequestDto {

    private String lectureTitle;
    private Integer lectureDuration;
    private MultipartFile lectureFile;

    public LectureEntity toEntity(Long sectionId, Long memberId) {
        return LectureEntity.createLecture(
                LectureTitle.of(lectureTitle),
                LectureDuration.of(lectureDuration),
                UnitId.of(),
                sectionId,
                memberId
        );
    }
}
