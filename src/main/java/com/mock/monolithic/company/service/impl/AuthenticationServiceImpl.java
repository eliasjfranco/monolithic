package com.mock.monolithic.company.service.impl;

import com.mock.monolithic.company.dto.AuthenticationResponse;
import com.mock.monolithic.company.dto.LoginRequest;
import com.mock.monolithic.company.entity.Company;
import com.mock.monolithic.company.entity.User;
import com.mock.monolithic.company.repository.CompanyRepository;
import com.mock.monolithic.company.service.AuthenticationService;
import com.mock.monolithic.shared.exception.CompanyDisabledException;
import com.mock.monolithic.shared.exception.UserNotFoundException;
import com.mock.security.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        Company company = retrieveCompany(loginRequest.username());
        isCompanyEnabled(company);
        User user = retrieveUser(company, loginRequest.username());
        isUserEnabled(user);
        isCredentialsMatched(loginRequest.password(), user.getPassword());

        return new AuthenticationResponse(jwtService.generateToken(loginRequest.username(), company.getId(), null, user.getRole().getName()));
    }

    private void isCompanyEnabled(Company company) {
        if (company.getEnabled().equals(Boolean.FALSE))
            throw new CompanyDisabledException();
    }

    private Company retrieveCompany(final String username) {
        return companyRepository.findByUserEmail(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new UserNotFoundException();
                });
    }

    private User retrieveUser(final Company company, final String username) {
        return company.getUser().stream()
                .filter(u -> u.getEmail().equals(username))
                .findAny()
                .orElseThrow(UserNotFoundException::new);
    }

    private void isUserEnabled(final User user) {
        if (user.getEnabled().equals(Boolean.FALSE))
            throw new UserNotFoundException();
    }

    private void isCredentialsMatched(final String password, final String password2) {
        if (!encoder.matches(password, password2))
            throw new BadCredentialsException("Username or password incorrect.");
    }
}
