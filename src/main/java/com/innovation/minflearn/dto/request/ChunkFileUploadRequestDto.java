package com.innovation.minflearn.dto.request;

public record ChunkFileUploadRequestDto (
        int totalChunks,
        Long totalFileSize,
        String checksum
) {
}
