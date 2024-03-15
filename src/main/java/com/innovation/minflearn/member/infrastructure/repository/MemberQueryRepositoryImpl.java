package com.innovation.minflearn.member.infrastructure.repository;

import com.innovation.minflearn.member.domain.entity.Member;
import com.innovation.minflearn.member.domain.entity.QMember;
import com.innovation.minflearn.member.domain.vo.Email;
import com.innovation.minflearn.member.domain.vo.Phone;
import com.innovation.minflearn.member.infrastructure.dto.response.GetMemberResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.innovation.minflearn.member.domain.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isEmailExist(Email email) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(member.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public boolean isPhoneExist(Phone phone) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(member.phone.eq(phone))
                .fetchFirst() != null;
    }

    @Override
    public Optional<Member> getMember(Email email) {

        Member result = queryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Long getMemberId(Email email) {
        return queryFactory
                .select(member.id)
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}
