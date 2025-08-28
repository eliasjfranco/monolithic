package com.mock.monolithic.shared.exception;

import org.springframework.security.core.AuthenticationException;

public class CompanyDisabledException extends AuthenticationException {

    public CompanyDisabledException() {
        super("Company is not enabled");
    }
}
