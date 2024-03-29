package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.exception.custom.section.SectionNotFoundException;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.validator.VideoValidator;
import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {

    private final JwtAuthProvider jwtAuthProvider;
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public void addLecture(
            Long sectionId,
            String authorizationHeader,
            MultipartFile file,
            AddLectureRequestDto addLectureRequestDto
    ) throws IOException {

        validateSectionExistence(sectionId);

        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        //TODO 확장자명 추출 로직 추가

        String storedFilename = UUID.randomUUID().toString();
        String savePath = "/Users/inhomoon/Downloads/" + storedFilename; //TODO 각 서버에 맞게 파일 경로를 동적으로 할당하는 코드 구현(로컬, 테스트, 클라우드)
        file.transferTo(new File(savePath)); //TODO 파일 분할 업로드 구현

        Long lectureId = lectureRepository.save(addLectureRequestDto.toEntity(sectionId, memberId)).id();
        lectureFileRepository.save(
                LectureFileEntity.createLectureFile(
                        OriginFilename.of(file.getOriginalFilename()),
                        StoredFilename.of(storedFilename),
                        lectureId
                )
        );
    }

    private void validateSectionExistence(Long sectionId) {
        boolean sectionExist = sectionRepository.existsById(sectionId);
        if (!sectionExist) {
            throw new SectionNotFoundException();
        }
    }
}
