package com.innovation.minflearn.repository.member;

import com.innovation.minflearn.entity.MemberEntity;
import com.innovation.minflearn.vo.member.Email;
import com.innovation.minflearn.vo.member.Phone;

import java.util.Optional;

public interface MemberQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);

    Optional<MemberEntity> getMember(Email email);
}
