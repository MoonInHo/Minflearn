package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.query.LectureFileQueryDto;
import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;
import com.innovation.minflearn.dto.request.GetFailedChunkRequestDto;
import com.innovation.minflearn.dto.response.GetFailedChunkResponseDto;
import com.innovation.minflearn.event.AllChunkFileHasUploadedEvent;
import com.innovation.minflearn.event.ChunkFileHasUploadedEvent;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.util.FileTotalSizeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureFileUploadService {

    @Value("${file.upload.dir}")
    private String uploadDirPath;

    private final LectureFileRepository lectureFileRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void uploadChunkFile(
            Long courseId,
            Long lectureFileId,
            int chunkNumber,
            MultipartFile file,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException {

        String tempDirPath = getTempDirPath(courseId, lectureFileId);

        storeUploadedFileChunk(file, tempDirPath, chunkNumber);

        publishChunkFileHasUploadedEvent(lectureFileId, chunkFileUploadRequestDto, tempDirPath);
    }

    public GetFailedChunkResponseDto getUploadFailedChunkIndex(
            Long courseId,
            Long lectureFileId,
            GetFailedChunkRequestDto getFailedChunkRequestDto
    ) throws IOException {

        String tempDirPath = getTempDirPath(courseId, lectureFileId);
        String originFilename = lectureFileRepository.getOriginFilename(lectureFileId);

        List<Integer> failedChunks = calculateFailedChunk(getFailedChunkRequestDto, tempDirPath, originFilename);
        if (failedChunks.isEmpty()) {
            publishAllChunkFileHasUploadedEvent(lectureFileId, getFailedChunkRequestDto, tempDirPath);
        }
        return new GetFailedChunkResponseDto(failedChunks);
    }

    private String getTempDirPath(Long courseId, Long lectureFileId) {
        return uploadDirPath + "/" + courseId + "-" + lectureFileId;
    }

    private void storeUploadedFileChunk(MultipartFile file, String tempDirPath, int chunkNumber) throws IOException {
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(tempDirPath, filename);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
    }

    private void publishChunkFileHasUploadedEvent(
            Long lectureFileId,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto,
            String tempDirPath
    ) {
        eventPublisher.publishEvent(
                new ChunkFileHasUploadedEvent(
                        lectureFileId,
                        tempDirPath,
                        chunkFileUploadRequestDto
                )
        );
    }

    public void checkAllChunkFileUploaded(
            Long lectureFileId,
            String tempDirPath,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException {

        Path tempDir = Paths.get(tempDirPath);
        int totalChunks = chunkFileUploadRequestDto.totalChunks();

        if (isUploadedChunkCountMatched(tempDir, totalChunks)) {
            synchronized (this) {
                if (isAllChunkUploadCompleted(chunkFileUploadRequestDto, tempDir)) {
                    mergeChunkFiles(lectureFileId, tempDirPath, totalChunks);
                }
            }
        }
    }

    private boolean isUploadedChunkCountMatched(Path tempDir, int totalChunks) throws IOException {
        int chunkFileCount = getChunkFileCount(tempDir);
        return chunkFileCount == totalChunks;
    }

    private int getChunkFileCount(Path tempDir) throws IOException {
        try (Stream<Path> fileList = Files.list(tempDir)) { // TODO Stream 의 sourceStage 가 끊임 없이 참조되는 이유 파악
            return Math.toIntExact(fileList.count());
        }
    }

    private boolean isAllChunkUploadCompleted(
            ChunkFileUploadRequestDto chunkFileUploadRequestDto,
            Path tempDir
    ) throws IOException {

        long totalFileSize = chunkFileUploadRequestDto.totalFileSize();
        long uploadedTotalChunkSize = getUploadedTotalChunkSize(tempDir);

        return totalFileSize == uploadedTotalChunkSize;
    }

    private Long getUploadedTotalChunkSize(Path tempDir) throws IOException {
        if (Files.isDirectory(tempDir)) {
            FileTotalSizeCalculator calculator = new FileTotalSizeCalculator();
            Files.walkFileTree(tempDir, calculator);
            return calculator.getTotalFileSize();
        }
        return 0L;
    }

    private void mergeChunkFiles(
            Long lectureFileId,
            String tempDirPath,
            int totalChunks
    ) throws IOException {

        LectureFileQueryDto filenameDto = lectureFileRepository.getFilename(lectureFileId);
        Path outputFile = createOutputFile(filenameDto.storedFilename());

        try (OutputStream outputStream = getMergeOutputStream(outputFile)) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkFile = getChunkFilePath(tempDirPath, filenameDto.originFilename(), i);
                Files.copy(chunkFile, outputStream);
            }
        } finally {
            FileUtils.deleteDirectory(new File(tempDirPath)); // TODO 임시 디렉토리가 어떠한 이유로 삭제 되지 않았을 경우 고민 (스케줄러, 타임아웃 등)
        }
    }

    private Path createOutputFile(String outputFilename) throws IOException {
        Path outputFile = Paths.get(uploadDirPath, outputFilename);
        if (Files.exists(outputFile)) {
            return outputFile;
        }
        return Files.createFile(outputFile);
    }

    private OutputStream getMergeOutputStream(Path outputFile) throws IOException {
        return Files.newOutputStream(outputFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private Path getChunkFilePath(String tempDirPath, String originFilename, int i) {
        return Paths.get(tempDirPath, originFilename + ".part" + i);
    }

    private List<Integer> calculateFailedChunk(
            GetFailedChunkRequestDto getFailedChunkRequestDto,
            String tempDirPath,
            String originFilename
    ) throws IOException {

        List<Integer> failedChunkFiles = new ArrayList<>();

        int totalChunks = getFailedChunkRequestDto.totalChunks();
        Long chunkSize = getFailedChunkRequestDto.chunkSize();

        for (int i = 0; i < totalChunks; i++) {

            Path chunkFile = getChunkFilePath(tempDirPath, originFilename, i);

            if (Files.notExists(chunkFile)) {
                failedChunkFiles.add(i);
                continue;
            }

            long uploadedChunkSize = Files.size(chunkFile);
            if (isUploadFailedLastChunk(getFailedChunkRequestDto, i, uploadedChunkSize)) {
                failedChunkFiles.add(i);
                break;
            }
            if (!isChunkSizeEquals(chunkSize, uploadedChunkSize)) {
                failedChunkFiles.add(i);
            }
        }
        return failedChunkFiles;
    }

    private boolean isUploadFailedLastChunk(
            GetFailedChunkRequestDto getFailedChunkRequestDto,
            int i,
            long uploadedChunkSize
    ) {
        int totalChunks = getFailedChunkRequestDto.totalChunks();
        if (!isLastChunkIndex(totalChunks, i)) {
            return false;
        }
        long lastChunkSize = calculateLastChunkSize(totalChunks, getFailedChunkRequestDto);

        return lastChunkSize != uploadedChunkSize;
    }

    private boolean isLastChunkIndex(int totalChunks, int i) {
        return i == totalChunks-1;
    }

    private long calculateLastChunkSize(int totalChunks, GetFailedChunkRequestDto getFailedChunkRequestDto) {
        long chunkSize = getFailedChunkRequestDto.chunkSize();
        long totalFileSize = getFailedChunkRequestDto.totalFileSize();

        return totalFileSize - (chunkSize * (totalChunks-1));
    }

    private boolean isChunkSizeEquals(Long chunkSize, Long uploadedChunkSize) {
        return chunkSize.equals(uploadedChunkSize);
    }

    private void publishAllChunkFileHasUploadedEvent(
            Long lectureFileId,
            GetFailedChunkRequestDto getFailedChunkRequestDto,
            String tempDirPath
    ) {
        eventPublisher.publishEvent(
                new AllChunkFileHasUploadedEvent(
                        lectureFileId,
                        tempDirPath,
                        getFailedChunkRequestDto
                )
        );
    }

    public void checkChunkFileMergeCompleted(
            Long lectureFileId,
            String tempDirPath,
            GetFailedChunkRequestDto getFailedChunkRequestDto
    ) throws IOException {

        String storedFilename = lectureFileRepository.getStoredFilename(lectureFileId);
        Path uploadFilePath = Paths.get(uploadDirPath, storedFilename);

        int totalChunks = getFailedChunkRequestDto.totalChunks();
        long chunkSize = getFailedChunkRequestDto.chunkSize();
        long totalFileSize = getFailedChunkRequestDto.totalFileSize();

        if (Files.notExists(uploadFilePath)) {
            mergeChunkFiles(lectureFileId, tempDirPath, totalChunks);
            return;
        }
        if (isMergeCompleted(uploadFilePath, totalFileSize)) {
            FileUtils.deleteDirectory(new File(tempDirPath));
            return;
        }
        mergeChunkFilesResume(lectureFileId, tempDirPath, totalChunks, chunkSize);
    }

    private boolean isMergeCompleted(Path uploadFilePath, long totalFileSize) throws IOException {
        long mergeFileSize = getMergeFileSize(uploadFilePath);
        return mergeFileSize == totalFileSize;
    }

    private long getMergeFileSize(Path uploadFilePath) throws IOException {
        return Files.size(uploadFilePath);
    }

    private void mergeChunkFilesResume(
            Long lectureFileId,
            String tempDirPath,
            int totalChunks,
            Long chunkSize
    ) throws IOException {

        LectureFileQueryDto filenameDto = lectureFileRepository.getFilename(lectureFileId);

        Path uploadFilePath = Paths.get(uploadDirPath, filenameDto.storedFilename());
        long mergedFileSize = Files.size(uploadFilePath);

        int currentChunkIndex = (int) (mergedFileSize / chunkSize);
        long startOffset = getStartOffset(mergedFileSize, chunkSize);

        try (OutputStream outputStream = Files.newOutputStream(uploadFilePath, StandardOpenOption.APPEND)) {
            for (int i = currentChunkIndex; i < totalChunks; i++) {
                if (startOffset != 0) {
                    String failedChunkFilePath = getFailedChunkFilePath(tempDirPath, currentChunkIndex, filenameDto);
                    appendFailedChunk(chunkSize, uploadFilePath, startOffset, failedChunkFilePath);
                    continue;
                }
                Path chunkFile = getChunkFilePath(tempDirPath, filenameDto.originFilename(), i);
                Files.copy(chunkFile, outputStream);
            }
        } finally {
            FileUtils.deleteDirectory(new File(tempDirPath));
        }
    }

    private long getStartOffset(long mergedFileSize, long chunkSize) {
        return mergedFileSize - ((mergedFileSize / chunkSize) * chunkSize);
    }

    private String getFailedChunkFilePath(String tempDirPath, int currentChunkIndex, LectureFileQueryDto filenameDto) {
        return tempDirPath + "/" + filenameDto.originFilename() + "part." + currentChunkIndex;
    }

    private void appendFailedChunk(
            Long chunkSize,
            Path uploadFilePath,
            long startOffset,
            String failedChunkFilePath
    ) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(failedChunkFilePath)) {
            byte[] chunkBytes = new byte[Math.toIntExact(chunkSize)];

            fileInputStream.skip(startOffset);
            fileInputStream.read(chunkBytes);

            Files.write(uploadFilePath, chunkBytes, StandardOpenOption.APPEND);
        }
    }
}