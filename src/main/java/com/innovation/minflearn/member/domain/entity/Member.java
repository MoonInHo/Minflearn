package com.innovation.minflearn.member.domain.entity;

import com.innovation.minflearn.member.domain.enums.Role;
import com.innovation.minflearn.member.domain.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Member(
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

    public static Member CreateMember(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address,
            Role role
    ) {
        return new Member(email, password, birthDate, phone, address, role);
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
