package com.innovation.minflearn.member.infrastructure.repository;

import com.innovation.minflearn.member.domain.entity.Member;
import com.innovation.minflearn.member.domain.vo.Email;
import com.innovation.minflearn.member.domain.vo.Phone;
import com.innovation.minflearn.member.infrastructure.dto.response.GetMemberResponseDto;

import java.util.Optional;

public interface MemberQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);

    Optional<Member> getMember(Email email);

    Long getMemberId(Email email);
}
