package com.innovation.minflearn.event;

import com.innovation.minflearn.dto.request.ChunkFileUploadRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChunkFileHasUploadedEvent {

    private Long lectureFileId;
    private String tempDirPath;
    private ChunkFileUploadRequestDto chunkFileUploadRequestDto;
}
