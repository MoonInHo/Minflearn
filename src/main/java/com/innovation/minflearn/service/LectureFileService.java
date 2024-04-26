package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
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
    private String uploadDir;

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public UploadLectureFileUrlResponseDto createFileMetadataAndTempDir(
            Long courseId,
            String authorizationHeader,
            MultipartFile file
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        verifyCourseOwnership(courseId, memberId);

        Long lectureFileId = saveLectureFileMetadata(courseId, file);
        createTempFileDir(getTempDirPath(lectureFileId));

        return UploadLectureFileUrlResponseDto.generateUploadUrl(courseId, lectureFileId);
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

    private void verifyCourseOwnership(Long courseId, Long memberId) {
        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }

    private String createOutputFilename(MultipartFile file) {
        String[] split = file.getOriginalFilename().split("\\.");
        return UUID.randomUUID() + "." + split[split.length - 1];
    }

    private String getTempDirPath(Long lectureFileId) {
        return uploadDir + "/" + lectureFileId;
    }

    private void createTempFileDir(String tempDir) {
        File dir = new File(tempDir);
        dir.mkdirs();
    }
}
