package com.innovation.minflearn.member.application.service;

import com.innovation.minflearn.exception.custom.member.DuplicateEmailException;
import com.innovation.minflearn.exception.custom.member.DuplicatePhoneException;
import com.innovation.minflearn.member.application.dto.CreateMemberRequestDto;
import com.innovation.minflearn.member.domain.repository.MemberRepository;
import com.innovation.minflearn.member.domain.vo.Email;
import com.innovation.minflearn.member.domain.vo.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(CreateMemberRequestDto createMemberRequestDto) {

        checkDuplicateEmail(createMemberRequestDto);
        checkDuplicatePhone(createMemberRequestDto);

        memberRepository.save(createMemberRequestDto.toEntity());
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
