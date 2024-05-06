package com.innovation.minflearn.dto.request;

public record GetFailedChunkRequestDto(
        int totalChunks,
        Long chunkSize,
        Long totalFileSize
) {
}
