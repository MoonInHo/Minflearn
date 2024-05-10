package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.exception.custom.course.CourseNotFoundException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LectureFileService {

    @Value("${file.upload.dir}")
    private String uploadDirPath;

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public UploadLectureFileUrlResponseDto createFileMetadataAndTempDir(
            Long courseId,
            String authorizationHeader,
            MultipartFile file
    ) {
        validateCourseExistence(courseId);
        verifyCourseOwnership(courseId, authorizationHeader);

        Long lectureFileId = saveLectureFileMetadata(courseId, file);
        createTempFileDir(getTempDirPath(courseId, lectureFileId));

        return UploadLectureFileUrlResponseDto.generateUploadUrl(courseId, lectureFileId);
    }

    private void validateCourseExistence(Long courseId) {
        boolean courseExist = courseRepository.isCourseExist(courseId);
        if (!courseExist) {
            throw new CourseNotFoundException();
        }
    }

    private void verifyCourseOwnership(Long courseId, String authorizationHeader) {

        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }

    private Long saveLectureFileMetadata(Long courseId, MultipartFile file) {
        return lectureFileRepository.save(
                LectureFileEntity.createLectureFile(
                        OriginFilename.of(file.getOriginalFilename()),
                        StoredFilename.of(createOutputFilename(file)),
                        courseId
                )
        ).id();
    }

    private String createOutputFilename(MultipartFile file) {
        String[] split = file.getOriginalFilename().split("\\.");
        return UUID.randomUUID() + "." + split[split.length - 1];
    }

    private String getTempDirPath(Long courseId, Long lectureFileId) {
        return uploadDirPath + "/" + courseId + "-" + lectureFileId;
    }

    private void createTempFileDir(String tempDirPath) {
        File dir = new File(tempDirPath);
        dir.mkdirs();
    }
}
