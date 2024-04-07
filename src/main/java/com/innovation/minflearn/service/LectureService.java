package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.dto.request.ChunkFileRequestDto;
import com.innovation.minflearn.entity.LectureCacheEntity;
import com.innovation.minflearn.entity.LectureEntity;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.exception.LectureCacheNotFoundException;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.exception.custom.section.SectionNotFoundException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.repository.redis.LectureCacheRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.vo.lecture.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LectureService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;
    private final LectureFileRepository lectureFileRepository;
    private final LectureCacheRepository lectureCacheRepository;

    @Transactional
    public void addLecture(
            Long courseId,
            Long sectionId,
            String key,
            String authorizationHeader,
            AddLectureRequestDto addLectureRequestDto
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        verifyCourseOwnership(courseId, memberId);
        validateSectionExistence(sectionId);

        lectureCacheRepository.save(
                new LectureCacheEntity(
                        key,
                        addLectureRequestDto.lectureTitle(),
                        addLectureRequestDto.lectureDuration(),
                        sectionId,
                        memberId
                )
        );
    }

    @Transactional
    public boolean uploadChunkFile( //TODO courseId, sectionId 사용 고민
            Long courseId,
            Long sectionId,
            MultipartFile chunkFile,
            ChunkFileRequestDto chunkFileRequestDto,
            String key
    ) throws IOException {

        String tempDir = uploadDir + "/" + key;

        createTempFileDir(tempDir);

        int chunkNumber = chunkFileRequestDto.chunkNumber();
        int totalChunks = chunkFileRequestDto.totalChunks();

        storeUploadedFileChunk(chunkFile, tempDir, chunkNumber);

        if (isLastChunks(chunkNumber, totalChunks)) {

            String outputFilename = createOutputFilename(chunkFile);
            Path outputFile = createOutputFile(outputFilename);

            mergeChunkFiles(chunkFile, tempDir, totalChunks, outputFile);

            Long lectureId = saveLectureInfo(key, courseId, sectionId);
            lectureFileRepository.save(
                    LectureFileEntity.createLectureFile(
                            OriginFilename.of(chunkFile.getOriginalFilename()),
                            StoredFilename.of(outputFilename),
                            lectureId
                    )
            );
            return true;
        } else {
            return false;
        }
    }

    public int getLastChunkNumber(Long courseId, Long sectionId, String key) { //TODO courseId, sectionId 사용 고민

        String[] list = getChunkFileList(key);

        return getLastChunkNumber(list);
    }

    private void verifyCourseOwnership(Long courseId, Long memberId) {
        boolean courseOwner = courseRepository.isCourseOwner(courseId, memberId);
        if (!courseOwner) {
            throw new CourseAccessDeniedException();
        }
    }

    private void validateSectionExistence(Long sectionId) {
        boolean sectionExist = sectionRepository.isSectionExist(sectionId);
        if (!sectionExist) {
            throw new SectionNotFoundException();
        }
    }

    private void createTempFileDir(String tempDir) {
        File dir = new File(tempDir);
        dir.mkdirs();
    }

    private void storeUploadedFileChunk(MultipartFile file, String tempDir, int chunkNumber) throws IOException {
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(tempDir, filename);
        Files.write(filePath, file.getBytes());
    }

    private boolean isLastChunks(int chunkNumber, int totalChunks) {
        return chunkNumber == totalChunks - 1;
    }

    private String createOutputFilename(MultipartFile file) {
        String[] split = file.getOriginalFilename().split("\\.");
        return UUID.randomUUID() + "." + split[split.length - 1];
    }

    private Path createOutputFile(String outputFilename) throws IOException {
        Path outputFile = Paths.get(uploadDir, outputFilename);
        return Files.createFile(outputFile);
    }

    private void mergeChunkFiles(
            MultipartFile file,
            String tempDir,
            int totalChunks,
            Path outputFile
    ) throws IOException {
        for (int i = 0; i < totalChunks; i++) {
            Path chunkFile = Paths.get(tempDir, file.getOriginalFilename() + ".part" + i);
            Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
        }
        FileUtils.deleteDirectory(new File(tempDir));
    }

    private Long saveLectureInfo(String key, Long courseId, Long sectionId) {

        LectureCacheEntity lectureCacheEntity = lectureCacheRepository.findById(key)
                .orElseThrow(LectureCacheNotFoundException::new);

        LectureEntity lecture = LectureEntity.createLecture(
                LectureTitle.of(lectureCacheEntity.getLectureTitle()),
                LectureDuration.of(lectureCacheEntity.getLectureDuration()),
                UnitId.of(courseId, sectionId),
                lectureCacheEntity.getSectionId(),
                lectureCacheEntity.getMemberId()
        );
        return lectureRepository.save(lecture).id();
    }

    private String[] getChunkFileList(String key) {
        Path temp = Paths.get("Video", key);
        return temp.toFile().list();
    }

    private int getLastChunkNumber(String[] list) {
        if (list == null) {
            return 0;
        }
        return list.length - 1;
    }
}
