package com.innovation.minflearn.repository.member;

import com.innovation.minflearn.entity.Member;
import com.innovation.minflearn.repository.member.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {
}
