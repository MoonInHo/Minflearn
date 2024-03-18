package com.innovation.minflearn.repository.jwt;

import com.innovation.minflearn.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
