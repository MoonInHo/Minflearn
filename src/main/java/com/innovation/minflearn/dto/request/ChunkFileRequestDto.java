package com.innovation.minflearn.dto.request;

public record ChunkFileRequestDto(
        int chunkNumber,
        int totalChunks
) {
}
