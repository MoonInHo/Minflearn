package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.AddLectureRequestDto;
import com.innovation.minflearn.entity.LectureFileEntity;
import com.innovation.minflearn.exception.custom.section.SectionNotFoundException;
import com.innovation.minflearn.repository.jpa.lecture.LectureFileRepository;
import com.innovation.minflearn.repository.jpa.lecture.LectureRepository;
import com.innovation.minflearn.repository.jpa.section.SectionRepository;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.vo.lecture.OriginFilename;
import com.innovation.minflearn.vo.lecture.StoredFilename;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final JwtAuthProvider jwtAuthProvider;
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;
    private final LectureFileRepository lectureFileRepository;

    @Transactional
    public boolean addLecture(
            String authorizationHeader,
            MultipartFile file,
            AddLectureRequestDto addLectureRequestDto
    ) throws IOException {

        validateSectionExistence(addLectureRequestDto.sectionId());

        Long memberId = jwtAuthProvider.extractMemberId(authorizationHeader);

        createUploadFileDir(uploadDir);

        int chunkNumber = addLectureRequestDto.chunkNumber();
        int totalChunks = addLectureRequestDto.totalChunks();

        storeUploadedFileChunk(file, chunkNumber);

        if (isLastChunks(chunkNumber, totalChunks)) {

            String outputFilename = createOutputFilename(file);
            Path outputFile = createOutputFile(outputFilename);

            mergeChunkFiles(file, totalChunks, outputFile);

            Long lectureId = lectureRepository.save(addLectureRequestDto.toEntity(memberId)).id();
            lectureFileRepository.save(
                    LectureFileEntity.createLectureFile(
                            OriginFilename.of(file.getOriginalFilename()),
                            StoredFilename.of(outputFilename),
                            lectureId
                    )
            );
            return true;
        } else {
            return false;
        }
    }

    private void validateSectionExistence(Long sectionId) {
        boolean sectionExist = sectionRepository.isSectionExist(sectionId);
        if (!sectionExist) {
            throw new SectionNotFoundException();
        }
    }

    private void createUploadFileDir(String uploadDir) {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void storeUploadedFileChunk(MultipartFile file, int chunkNumber) throws IOException {
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(uploadDir, filename);
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

    private void mergeChunkFiles(MultipartFile file, int totalChunks, Path outputFile) throws IOException {
        for (int i = 0; i < totalChunks; i++) {
            Path chunkFile = Paths.get(uploadDir, file.getOriginalFilename() + ".part" + i);
            Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
            Files.delete(chunkFile);
        }
    }
}
