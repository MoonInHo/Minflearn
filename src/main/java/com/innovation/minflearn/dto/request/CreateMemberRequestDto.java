package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.MemberEntity;
import com.innovation.minflearn.enums.Role;
import com.innovation.minflearn.vo.member.*;

public record CreateMemberRequestDto(
        String email,
        String password,
        String birthDate,
        String phone,
        String address,
        String addressDetail
) {
    public MemberEntity toEntity() {
        return MemberEntity.CreateMember(
                Email.of(email),
                Password.of(password),
                BirthDate.of(birthDate),
                Phone.of(phone),
                Address.of(address, addressDetail),
                Role.ROLE_USER
        );
    }
}
