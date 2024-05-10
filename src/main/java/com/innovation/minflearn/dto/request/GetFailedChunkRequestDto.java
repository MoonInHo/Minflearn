package com.innovation.minflearn.dto.request;

public record GetFailedChunkRequestDto(
        int totalChunks,
        long chunkSize,
        long totalFileSize
) {
}
