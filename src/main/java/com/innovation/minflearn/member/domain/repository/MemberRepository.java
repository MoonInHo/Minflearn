package com.innovation.minflearn.member.domain.repository;

import com.innovation.minflearn.member.domain.entity.Member;
import com.innovation.minflearn.member.infrastructure.repository.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {
}
