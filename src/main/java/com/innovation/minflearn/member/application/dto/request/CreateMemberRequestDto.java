package com.innovation.minflearn.member.application.dto.request;

import com.innovation.minflearn.member.domain.entity.Member;
import com.innovation.minflearn.member.domain.enums.Role;
import com.innovation.minflearn.member.domain.vo.*;
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

    public Member toEntity() {
        return Member.CreateMember(
                Email.of(email),
                Password.of(password),
                BirthDate.of(birthDate),
                Phone.of(phone),
                Address.of(address, addressDetail),
                Role.ROLE_USER
        );
    }
}
