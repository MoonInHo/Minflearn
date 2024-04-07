package com.innovation.minflearn.repository.redis;

import com.innovation.minflearn.entity.LectureCacheEntity;
import org.springframework.data.repository.CrudRepository;

public interface LectureCacheRepository extends CrudRepository<LectureCacheEntity, String> {
}
