package com.innovation.minflearn.dto.response;

import java.util.List;

public record GetFailedChunkResponseDto(
        List<Integer> failedChunkIndex
) {
}
