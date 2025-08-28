package com.mock.monolithic.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends User {

    private final Long userId;
    private final Integer companyId;

    public CustomUserDetails(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities, Integer companyId) {
        super(email, password, true, true, true, true, authorities);
        this.userId = id;
        this.companyId = companyId;
    }
}
