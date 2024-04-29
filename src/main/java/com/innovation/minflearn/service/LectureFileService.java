package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.LectureFileQueryDto;
import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;
import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.event.ChunkFileHasUploadedEvent;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.util.FileSizeCalculator;
import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureFileService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final JwtAuthProvider jwtAuthProvider;
    private final CourseRepository courseRepository;
    private final LectureFileRepository lectureFileRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UploadLectureFileUrlResponseDto createFileMetadataAndTempDir(
            Long courseId,
            String authorizationHeader,
            MultipartFile file
    ) {
        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);
        verifyCourseOwnership(courseId, memberId);

        Long lectureFileId = saveLectureFileMetadata(courseId, file);
        createTempFileDir(getTempDirPath(courseId, lectureFileId));

        return UploadLectureFileUrlResponseDto.generateUploadUrl(courseId, lectureFileId);
    }

    public void uploadChunkFile(
            Long courseId,
            Long lectureFileId,
            int chunkNumber,
            MultipartFile file,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException {

        String tempDirPath = getTempDirPath(courseId, lectureFileId);

        storeUploadedFileChunk(file, tempDirPath, chunkNumber);

        eventPublisher.publishEvent(
                new ChunkFileHasUploadedEvent(lectureFileId, tempDirPath, chunkFileUploadRequestDto)
        );
    }

    public void mergeUploadedChunkFile(
            Long lectureFileId,
            String tempDirPath,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException {

        int totalChunks = chunkFileUploadRequestDto.totalChunks();
        Long totalFileSize = chunkFileUploadRequestDto.totalFileSize();
        Path tempDir = Paths.get(tempDirPath);

        if (isChunkCountEquals(totalChunks, getChunkFileCount(tempDir))) {
            synchronized (this) {
                if (isTotalChunkSizeEquals(totalFileSize, getUploadedTotalChunkSize(tempDir))) {
                    LectureFileQueryDto filenameDto = lectureFileRepository.getFilename(lectureFileId);
                    mergeChunkFiles(filenameDto, tempDirPath, totalChunks);
                }
            }
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

    private String getTempDirPath(Long courseId, Long lectureFileId) {
        return uploadDir + "/" + courseId + "-" + lectureFileId;
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

    private boolean isChunkCountEquals(int totalChunks, Long chunkFileCount) {
        return chunkFileCount == totalChunks;
    }

    private Long getChunkFileCount(Path tempDir) throws IOException {
        try (Stream<Path> stream = Files.list(tempDir)) {
            return stream.count();
        }
    }

    private boolean isTotalChunkSizeEquals(Long totalFileSize, Long totalChunkSize) {
        return totalChunkSize.equals(totalFileSize);
    }

    private Long getUploadedTotalChunkSize(Path tempDir) throws IOException {
        FileSizeCalculator calculator = new FileSizeCalculator();
        Files.walkFileTree(tempDir, calculator);
        return calculator.getTotalFileSize();
    }

    private Path createOutputFile(String outputFilename) throws IOException {
        Path outputFile = Paths.get(uploadDir, outputFilename);
        return Files.createFile(outputFile);
    }

    private void mergeChunkFiles(
            LectureFileQueryDto filenameDto,
            String tempDir,
            int totalChunks
    ) throws IOException {

        Path outputFile = createOutputFile(filenameDto.storedFilename());

        try (OutputStream outputStream = Files.newOutputStream(outputFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkFile = Paths.get(tempDir, filenameDto.originFilename() + ".part" + i);
                Files.copy(chunkFile, outputStream);
            }
        } finally {
            FileUtils.deleteDirectory(new File(tempDir));
        }
    }
}
