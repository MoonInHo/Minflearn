package com.innovation.minflearn.repository.jpa.member;

import com.innovation.minflearn.entity.MemberEntity;
import com.innovation.minflearn.vo.member.Email;
import com.innovation.minflearn.vo.member.Phone;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.innovation.minflearn.entity.QMemberEntity.memberEntity;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isEmailExist(Email email) {
        return queryFactory
                .selectOne()
                .from(memberEntity)
                .where(memberEntity.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public boolean isPhoneExist(Phone phone) {
        return queryFactory
                .selectOne()
                .from(memberEntity)
                .where(memberEntity.phone.eq(phone))
                .fetchFirst() != null;
    }

    @Override
    public Optional<MemberEntity> getMember(Email email) {

        MemberEntity result = queryFactory
                .selectFrom(memberEntity)
                .where(memberEntity.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Email getEmail(Long memberId) {
        return queryFactory
                .select(memberEntity.email)
                .from(memberEntity)
                .where(memberEntity.id.eq(memberId))
                .fetchOne();
    }
}
