package com.innovation.minflearn.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AccountContext extends User {

    private final Long memberId;

    public AccountContext(String email, String password, Collection<? extends GrantedAuthority> authorities, Long memberId) {
        super(email, password, authorities);
        this.memberId = memberId;
    }
}