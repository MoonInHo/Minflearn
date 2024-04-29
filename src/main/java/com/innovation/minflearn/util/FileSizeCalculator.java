package com.innovation.minflearn.util;

import lombok.Getter;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@Getter
public class FileSizeCalculator extends SimpleFileVisitor<Path> {

    private long totalFileSize = 0;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        totalFileSize += attrs.size();
        return FileVisitResult.CONTINUE;
    }
}
