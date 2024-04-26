package com.innovation.minflearn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = 2115029486L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final com.innovation.minflearn.vo.member.QAddress address;

    public final com.innovation.minflearn.vo.member.QBirthDate birthDate;

    public final com.innovation.minflearn.vo.member.QEmail email;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.innovation.minflearn.vo.member.QPassword password;

    public final com.innovation.minflearn.vo.member.QPhone phone;

    public final EnumPath<com.innovation.minflearn.enums.Role> role = createEnum("role", com.innovation.minflearn.enums.Role.class);

    public QMemberEntity(String variable) {
        this(MemberEntity.class, forVariable(variable), INITS);
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberEntity(PathMetadata metadata, PathInits inits) {
        this(MemberEntity.class, metadata, inits);
    }

    public QMemberEntity(Class<? extends MemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.innovation.minflearn.vo.member.QAddress(forProperty("address")) : null;
        this.birthDate = inits.isInitialized("birthDate") ? new com.innovation.minflearn.vo.member.QBirthDate(forProperty("birthDate")) : null;
        this.email = inits.isInitialized("email") ? new com.innovation.minflearn.vo.member.QEmail(forProperty("email")) : null;
        this.password = inits.isInitialized("password") ? new com.innovation.minflearn.vo.member.QPassword(forProperty("password")) : null;
        this.phone = inits.isInitialized("phone") ? new com.innovation.minflearn.vo.member.QPhone(forProperty("phone")) : null;
    }

}

