package com.innovation.minflearn.event;

import com.innovation.minflearn.service.LectureFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LectureFileEventListener {

    private final LectureFileService lectureFileService;

    @Async
    @EventListener //TODO 이벤트 수행 도중 실패했을때는 어떻게 처리할 것인가?
    public void onProductHasUpdatedEvent(ChunkFileHasUploadedEvent chunkFileHasUploadedEvent) throws IOException {
        lectureFileService.mergeUploadedChunkFile(
                chunkFileHasUploadedEvent.getLectureFileId(),
                chunkFileHasUploadedEvent.getTempDirPath(),
                chunkFileHasUploadedEvent.getChunkFileUploadRequestDto()
        );
    }
}
