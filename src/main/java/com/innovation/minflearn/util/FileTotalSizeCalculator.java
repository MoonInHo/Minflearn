package com.innovation.minflearn.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@Getter
@Slf4j
public class FileTotalSizeCalculator extends SimpleFileVisitor<Path> {

    private long totalFileSize = 0;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        totalFileSize += attrs.size();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        log.error("접근에 실패한 파일 : {} - {}", file, exc);
        return FileVisitResult.CONTINUE;
    }
}
