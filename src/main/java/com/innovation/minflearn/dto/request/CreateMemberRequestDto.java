package com.innovation.minflearn.dto.request;

import com.innovation.minflearn.entity.MemberEntity;
import com.innovation.minflearn.enums.Role;
import com.innovation.minflearn.vo.member.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequestDto {

    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;

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
