package com.innovation.minflearn.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken", timeToLive = 1209600)
@NoArgsConstructor
public class RefreshTokenEntity {

    @Id
    private String refreshToken;

    private Long memberId;

    @TimeToLive
    private Long expiration;

    public RefreshTokenEntity(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
        this.expiration = 1209600L;
    }
}
