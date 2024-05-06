package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.query.LectureFileQueryDto;
import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;
import com.innovation.minflearn.dto.request.GetFailedChunkRequestDto;
import com.innovation.minflearn.dto.response.GetFailedChunkResponseDto;
import com.innovation.minflearn.dto.response.UploadLectureFileUrlResponseDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.event.ChunkFileHasUploadedEvent;
import com.innovation.minflearn.event.AllChunkFileHasUploadedEvent;
import com.innovation.minflearn.exception.custom.course.CourseAccessDeniedException;
import com.innovation.minflearn.repository.jpa.cource.CourseRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.util.FileTotalSizeCalculator;
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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureFileUploadService {

    @Value("${file.upload.dir}")
    private String uploadDirPath;

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

    public GetFailedChunkResponseDto getUploadFailedChunkIndex(
            Long courseId,
            Long lectureFileId,
            GetFailedChunkRequestDto getFailedChunkRequestDto
    ) throws IOException {

        String originFilename = lectureFileRepository.getOriginFilename(lectureFileId);
        String tempDirPath = getTempDirPath(courseId, lectureFileId);

        List<Integer> failedChunks = getFailedChunks(getFailedChunkRequestDto, tempDirPath, originFilename);
        if (failedChunks.isEmpty()) {
            eventPublisher.publishEvent(
                    new AllChunkFileHasUploadedEvent(lectureFileId, tempDirPath, getFailedChunkRequestDto)
            );
        }
        return new GetFailedChunkResponseDto(failedChunks);
    }

    /**
     * 전체 chunk 업로드 완료 여부 확인
     * 업로드 완료시 병합 수행
     */
    public void checkAllChunkFileUploaded(
            Long lectureFileId,
            String tempDirPath,
            ChunkFileUploadRequestDto chunkFileUploadRequestDto
    ) throws IOException {

        int totalChunks = chunkFileUploadRequestDto.totalChunks();
        Path tempDir = Paths.get(tempDirPath);
        int chunkFileCount = getChunkFileCount(tempDir);

        if (isChunkCountEquals(totalChunks, chunkFileCount)) {
            synchronized (this) {
                Long totalFileSize = chunkFileUploadRequestDto.totalFileSize();
                LectureFileQueryDto filenameDto = lectureFileRepository.getFilename(lectureFileId);

                if (isTotalChunkSizeEquals(totalFileSize, getUploadedTotalChunkSize(tempDir))) {
                    mergeChunkFiles(filenameDto, tempDirPath, totalChunks);
                }
            }
        }
    }

    /**
     * chunk 파일이 모두 업로드 완료된 상태에서만 호출
     * 병합 파일이 존재할 경우 -> 이어쓰기 수행
     * 병합이 완료된 상태일 경우 -> 임시 디렉토리 삭제
     * chunk 파일 업로드만 완료된 상태일 경우 -> 병합 수행
     */
    public void checkChunkFileMergeCompleted(
            Long lectureFileId,
            String tempDirPath,
            GetFailedChunkRequestDto getFailedChunkRequestDto
    ) throws IOException {

        LectureFileQueryDto filenameDto = lectureFileRepository.getFilename(lectureFileId);
        Path uploadFilePath = Paths.get(uploadDirPath, filenameDto.storedFilename());

        int totalChunks = getFailedChunkRequestDto.totalChunks();
        Long chunkSize = getFailedChunkRequestDto.chunkSize();
        Long totalFileSize = getFailedChunkRequestDto.totalFileSize();

        if (isMergeFileExist(uploadFilePath)) {
            if (isMergeCompleted(getMergeFileSize(uploadFilePath), totalFileSize)) {
                FileUtils.deleteDirectory(new File(tempDirPath));
                return;
            }
            mergeChunkFilesResume(filenameDto, tempDirPath, totalChunks, chunkSize);
            return;
        }
        mergeChunkFiles(filenameDto, tempDirPath, totalChunks);
    }

    private void verifyCourseOwnership(Long courseId, Long memberId) {
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

    private void createTempFileDir(String tempDirPath) {
        File dir = new File(tempDirPath);
        dir.mkdirs();
    }

    private String getTempDirPath(Long courseId, Long lectureFileId) {
        return uploadDirPath + "/" + courseId + "-" + lectureFileId;
    }

    private void storeUploadedFileChunk(MultipartFile file, String tempDirPath, int chunkNumber) throws IOException {
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(tempDirPath, filename);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
    }

    private int getChunkFileCount(Path tempDir) throws IOException {
        try (Stream<Path> fileList = Files.list(tempDir)) { // TODO Stream 의 sourceStage 가 끊임 없이 참조되는 이유 파악
            return Math.toIntExact(fileList.count());
        }
    }

    /**
     * 디렉토리 안의 파일의 용량을 순차적으로 더해서 전체 용량 계산
     */
    private Long getUploadedTotalChunkSize(Path tempDir) throws IOException {
        if (Files.isDirectory(tempDir)) {
            FileTotalSizeCalculator calculator = new FileTotalSizeCalculator();
            Files.walkFileTree(tempDir, calculator);
            return calculator.getTotalFileSize();
        }
        return 0L;
    }

    /** 병합 메소드
     * 새 파일 생성 후 해당 파일에 chunk 파일을 순차적으로 복사
     * 모든 작업이 완료되면 임시 디렉토리 삭제
     */
    private void mergeChunkFiles(
            LectureFileQueryDto filenameDto,
            String tempDirPath,
            int totalChunks
    ) throws IOException {

        Path outputFile = createOutputFile(filenameDto.storedFilename());

        try (OutputStream outputStream = getMergeOutputStream(outputFile)) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkFile = getChunkFilePath(tempDirPath, filenameDto, i);
                Files.copy(chunkFile, outputStream);
            }
        } finally {
            FileUtils.deleteDirectory(new File(tempDirPath)); // TODO 임시 디렉토리가 어떠한 이유로 삭제 되지 않았을 경우 고민 (스케줄러, 타임아웃 등)
        }
    }

    /** 병합 이어쓰기 메소드
     * 진행중이던 chunk index 부터 병합 수행
     * 병합이 진행중이던 chunk 파일이 존재할 경우 -> 실패 내용 이어쓰기
     */
    private void mergeChunkFilesResume(
            LectureFileQueryDto filenameDto,
            String tempDirPath,
            int totalChunks,
            Long chunkSize
    ) throws IOException {

        Path uploadFilePath = Paths.get(uploadDirPath, filenameDto.storedFilename());

        long mergedFileSize = Files.size(uploadFilePath);
        long uploadedBytes = (mergedFileSize / chunkSize) * chunkSize;

        int currentChunkIndex = (int) (mergedFileSize / chunkSize);
        long startOffset = mergedFileSize - uploadedBytes;

        String failedChunkFilePath = getFailedChunkFilePath(tempDirPath, currentChunkIndex, filenameDto);

        try (OutputStream outputStream = Files.newOutputStream(uploadFilePath, StandardOpenOption.APPEND)) {
            for (int i = currentChunkIndex; i < totalChunks; i++) {
                if (startOffset != 0) {
                    appendFailedChunk(chunkSize, uploadFilePath, startOffset, failedChunkFilePath);
                    continue;
                }
                Path chunkFile = getChunkFilePath(tempDirPath, filenameDto, i);
                Files.copy(chunkFile, outputStream);
            }
        } finally {
            FileUtils.deleteDirectory(new File(tempDirPath));
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

    private Path getChunkFilePath(String tempDirPath, LectureFileQueryDto filenameDto, int i) {
        return Paths.get(tempDirPath, filenameDto.originFilename() + ".part" + i);
    }

    private Path getChunkFilePath(String tempDirPath, String originFilename, int i) {
        return Paths.get(tempDirPath, originFilename + ".part" + i);
    }

    /**
     * 1. chunk index 에 해당되는 파일이 존재하지 않을 경우
     * 2. chunk size 가 전송 받은 size와 일치하지 않을 경우
     */
    private List<Integer> getFailedChunks(
            GetFailedChunkRequestDto getFailedChunkRequestDto,
            String tempDirPath,
            String originFilename
    ) throws IOException {

        List<Integer> failedChunkFiles = new ArrayList<>();

        int totalChunks = getFailedChunkRequestDto.totalChunks();
        Long chunkSize = getFailedChunkRequestDto.chunkSize();
        Long totalFileSize = getFailedChunkRequestDto.totalFileSize();

        for (int i = 0; i < totalChunks; i++) {
            Path chunkFile = getChunkFilePath(tempDirPath, originFilename, i);
            if (Files.notExists(chunkFile)) {
                failedChunkFiles.add(i);
                continue;
            }
            if (isLastChunkIndex(totalChunks, i)) {
                Long lastChunkSize = calculateLastChunkSize(totalChunks, chunkSize, totalFileSize);
                Long uploadedLastChunkSize = Files.size(chunkFile);
                if (!isLastChunkSizeEquals(uploadedLastChunkSize, lastChunkSize)) {
                    failedChunkFiles.add(i);
                }
                break;
            }
            long uploadedChunkSize = Files.size(chunkFile);
            if (!isChunkSizeEquals(chunkSize, uploadedChunkSize)) {
                failedChunkFiles.add(i);
            }
        }
        return failedChunkFiles;
    }

    private Long calculateLastChunkSize(int totalChunks, Long chunkSize, Long totalFileSize) {
        return totalFileSize - (chunkSize * (totalChunks - 1));
    }

    private Long getMergeFileSize(Path uploadFilePath) throws IOException {
        return Files.size(uploadFilePath);
    }

    private String getFailedChunkFilePath(String tempDirPath, int currentChunkIndex, LectureFileQueryDto filenameDto) {
        return tempDirPath + "/" + filenameDto.originFilename() + "part." + currentChunkIndex;
    }

    /**
     * 실패한 index에 해당하는 chunk 파일 스트림을 생성
     * 스트림을 offset 만큼 이동한 뒤 나머지 값을 읽어 새로운 바이트 배열에 할당 후 이어쓰기 수행
     * @param startOffset - 병합 도중 실패한 chunk 파일의 성공 부분 offset
     */
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

    private boolean isChunkCountEquals(int totalChunks, int chunkFileCount) {
        return chunkFileCount == totalChunks;
    }

    private boolean isTotalChunkSizeEquals(Long totalFileSize, Long totalChunkSize) {
        return totalChunkSize.equals(totalFileSize);
    }

    private boolean isLastChunkIndex(int totalChunks, int i) {
        return i == totalChunks - 1;
    }

    private boolean isLastChunkSizeEquals(Long uploadedLastChunkSize, Long lastChunkSize) {
        return lastChunkSize.equals(uploadedLastChunkSize);
    }

    private boolean isChunkSizeEquals(Long chunkSize, Long uploadedChunkSize) {
        return chunkSize.equals(uploadedChunkSize);
    }

    private boolean isMergeFileExist(Path uploadFilePath) {
        return Files.exists(uploadFilePath);
    }

    private boolean isMergeCompleted(Long mergeFileSize, Long totalFileSize) {
        return mergeFileSize.equals(totalFileSize);
    }
}