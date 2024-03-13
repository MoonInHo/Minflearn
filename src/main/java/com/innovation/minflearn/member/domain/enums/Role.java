package com.innovation.minflearn.member.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

    ROLE_USER;

    public SimpleGrantedAuthority createRole() {
        return new SimpleGrantedAuthority(ROLE_USER.name());
    }
}
