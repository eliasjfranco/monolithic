package com.mock.monolithic.company.service.impl;

import com.mock.monolithic.company.entity.Company;
import com.mock.monolithic.company.entity.CustomUserDetails;
import com.mock.monolithic.company.entity.User;
import com.mock.monolithic.company.repository.CompanyRepository;
import com.mock.monolithic.shared.exception.CompanyDisabledException;
import com.mock.monolithic.shared.exception.UserNotFoundException;
import com.mock.security.config.JwtAuthProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;
    private final JwtAuthProperties jwtProps;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = companyRepository.findByUserEmail(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username {}", username);
                    return new UserNotFoundException();
                });

        if (company.getEnabled().equals(Boolean.FALSE))
            throw new CompanyDisabledException();

        User user = company.getUser().stream()
                .filter(u -> u.getEmail().equals(username))
                .findAny()
                .orElseThrow(UserNotFoundException::new);

        if (user.getEnabled().equals(Boolean.FALSE))
            throw new UserNotFoundException();

        final List<SimpleGrantedAuthority> role = Arrays.asList(new SimpleGrantedAuthority(jwtProps.getRolePrefix() + user.getRole()));

        return new CustomUserDetails(Long.valueOf(user.getId()), user.getEmail(), user.getPassword(), role, company.getId());
    }
}