package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.entity.LectureFile;
import com.innovation.minflearn.repository.lecture.LectureFileRepository;
import com.innovation.minflearn.repository.lecture.LectureRepository;
import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public void addLecture(Long courseId, MultipartFile lectureFile, AddLectureRequestDto addLectureRequestDto) throws IOException {

        String originalFilename = lectureFile.getOriginalFilename();
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;
        String savePath = "/Users/inhomoon/Downloads/" + storedFilename; //TODO 경로 변경 예정
        lectureFile.transferTo(new File(savePath));

        Long lectureId = lectureRepository.save(addLectureRequestDto.toEntity(courseId)).id();
        lectureFileRepository.save(
                LectureFile.createLectureFile(
                        OriginFilename.of(originalFilename),
                        StoredFilename.of(storedFilename),
                        lectureId
                )
        );
    }
}