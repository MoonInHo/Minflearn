package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.CreateMemberRequestDto;
import com.innovation.minflearn.repository.jpa.member.MemberRepository;
import com.innovation.minflearn.exception.custom.member.DuplicateEmailException;
import com.innovation.minflearn.exception.custom.member.DuplicatePhoneException;
import com.innovation.minflearn.entity.MemberEntity;
import com.innovation.minflearn.vo.member.Email;
import com.innovation.minflearn.vo.member.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(CreateMemberRequestDto createMemberRequestDto) {

        checkDuplicateEmail(createMemberRequestDto);
        checkDuplicatePhone(createMemberRequestDto);

        MemberEntity member = createMemberRequestDto.toEntity();
        member.encryptPassword(passwordEncoder);

        memberRepository.save(member);
    }

    private void checkDuplicateEmail(CreateMemberRequestDto createMemberRequestDto) {
        boolean emailExist = memberRepository.isEmailExist(Email.of(createMemberRequestDto.getEmail()));
        if (emailExist) {
            throw new DuplicateEmailException();
        }
    }

    private void checkDuplicatePhone(CreateMemberRequestDto createMemberRequestDto) {
        boolean phoneExist = memberRepository.isPhoneExist(Phone.of(createMemberRequestDto.getPhone()));
        if (phoneExist) {
            throw new DuplicatePhoneException();
        }
    }
}
