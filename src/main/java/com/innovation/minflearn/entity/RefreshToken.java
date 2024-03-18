package com.innovation.minflearn.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken", timeToLive = 1209600)
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String refreshToken;

    private String email;

    @TimeToLive
    private Long expiration;

    public RefreshToken(String refreshToken, String email) {
        this.refreshToken = refreshToken;
        this.email = email;
        this.expiration = 1209600L;
    }
}
