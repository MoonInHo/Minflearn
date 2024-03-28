package com.innovation.minflearn.repository.jpa.member;

import com.innovation.minflearn.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberQueryRepository {
}
