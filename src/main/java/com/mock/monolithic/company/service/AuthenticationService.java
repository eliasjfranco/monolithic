package com.mock.monolithic.company.service;

import com.mock.monolithic.company.dto.AuthenticationResponse;
import com.mock.monolithic.company.dto.LoginRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticate(LoginRequest loginRequest);
}
