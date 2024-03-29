package com.innovation.minflearn.entity;

import com.innovation.minflearn.enums.Role;
import com.innovation.minflearn.vo.member.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column(nullable = false, unique = true)
    private Email email;

    @Embedded
    @Column(nullable = false)
    private Password password;

    @Embedded
    private BirthDate birthDate;

    @Embedded
    @Column(nullable = false, unique = true)
    private Phone phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private MemberEntity(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address,
            Role role
    ) {
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public static MemberEntity CreateMember(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address,
            Role role
    ) {
        return new MemberEntity(email, password, birthDate, phone, address, role);
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = password.encryptPassword(passwordEncoder);
    }

    public List<GrantedAuthority> createRole() {
        return Collections.singletonList(role.createRole());
    }

    public Long id() {
        return id;
    }

    public String password() {
        return password.password();
    }
}
