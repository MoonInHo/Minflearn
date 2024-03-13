package com.innovation.minflearn.member.domain.repository;

import com.innovation.minflearn.member.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
