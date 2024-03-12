package com.innovation.minflearn.member.infrastructure.repository;

import com.innovation.minflearn.member.domain.vo.Email;
import com.innovation.minflearn.member.domain.vo.Phone;

public interface MemberQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);
}
