package com.innovation.minflearn.member.application.service;

import com.innovation.minflearn.exception.custom.member.DuplicateEmailException;
import com.innovation.minflearn.exception.custom.member.DuplicatePhoneException;
import com.innovation.minflearn.member.application.dto.CreateMemberRequestDto;
import com.innovation.minflearn.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[유닛 테스트] - 회원 서비스")
class MemberServiceTest {

    private CreateMemberRequestDto createMemberRequestDto;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void serMemberInfo() {

        createMemberRequestDto = new CreateMemberRequestDto(
                "test123@gmail.com",
                "testPassword123!",
                "1995.11.11",
                "010-1234-5678",
                "서울특별시 강남구 강남대로",
                "지하396"
        );
    }

    @Test
    @DisplayName("이메일 중복 확인 - 중복된 이메일로 회원가입 시도시 예외 발생")
    void duplicateEmail_signUp_throwException() {
        //given
        given(memberRepository.isEmailExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> memberService.signUp(createMemberRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicateEmailException.class);
        assertThat(throwable).hasMessage("이미 사용중인 이메일 입니다.");
    }

    @Test
    @DisplayName("연락처 중복 확인 - 중복된 연락처로 회원가입 시도시 예외 발생")
    void duplicatePhone_signUp_throwException() {
        given(memberRepository.isPhoneExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> memberService.signUp(createMemberRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicatePhoneException.class);
        assertThat(throwable).hasMessage("해당 연락처로 가입 정보가 존재합니다.");
    }

    @Test
    @DisplayName(" - 올바른 정보로 회원가입 시도시 사용자 정보 저장")
    void properInfo_signUp_saveUserInfo() {
        //given
        given(memberRepository.isEmailExist(any())).willReturn(false);
        given(memberRepository.isPhoneExist(any())).willReturn(false);

        //when
        memberService.signUp(createMemberRequestDto);

        //then
        verify(memberRepository, times(1)).save(any());
    }
}