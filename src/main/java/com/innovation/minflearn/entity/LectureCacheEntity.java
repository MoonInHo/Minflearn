package com.innovation.minflearn.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "lectureCache", timeToLive = 43200)
@NoArgsConstructor
public class LectureCacheEntity {

    @Id
    private String id;

    private String lectureTitle;

    private Integer lectureDuration;

    private Long sectionId;

    private Long memberId;

    @TimeToLive
    private Long expiration;

    public LectureCacheEntity(
            String key,
            String lectureTitle,
            Integer lectureDuration,
            Long sectionId,
            Long memberId
    ) {
        this.id = key;
        this.lectureTitle = lectureTitle;
        this.lectureDuration = lectureDuration;
        this.sectionId = sectionId;
        this.memberId = memberId;
        this.expiration = 43200L;
    }
}
