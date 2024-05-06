package com.innovation.minflearn.event;

import com.innovation.minflearn.service.LectureFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LectureFileEventListener {

    private final LectureFileUploadService lectureFileService;

    @Async
    @EventListener //TODO 이벤트 수행 도중 실패했을때는 어떻게 처리할 것인가? -> 병합이 진행되지 않음을 확인할 방법 필요
    public void chunkFileUploaded(ChunkFileHasUploadedEvent event) throws IOException {
        lectureFileService.checkAllChunkFileUploaded(
                event.lectureFileId(),
                event.tempDirPath(),
                event.chunkFileUploadRequestDto()
        );
    }

    @Async
    @EventListener
    public void allChunkFileUploaded(AllChunkFileHasUploadedEvent event) throws IOException {
        lectureFileService.checkChunkFileMergeCompleted(
                event.lectureFileId(),
                event.tempDirPath(),
                event.getFailedChunkRequestDto()
        );
    }
}
