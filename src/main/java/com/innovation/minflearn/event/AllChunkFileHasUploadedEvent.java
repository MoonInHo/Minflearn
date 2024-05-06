package com.innovation.minflearn.event;

import com.innovation.minflearn.dto.request.GetFailedChunkRequestDto;

public record AllChunkFileHasUploadedEvent(
        Long lectureFileId,
        String tempDirPath,
        GetFailedChunkRequestDto getFailedChunkRequestDto
) {
}
