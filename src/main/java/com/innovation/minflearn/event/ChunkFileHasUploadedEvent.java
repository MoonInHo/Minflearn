package com.innovation.minflearn.event;

import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;

public record ChunkFileHasUploadedEvent(
        Long lectureFileId,
        String tempDirPath,
        ChunkFileUploadRequestDto chunkFileUploadRequestDto
) {
}
