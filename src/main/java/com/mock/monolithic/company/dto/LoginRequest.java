package com.mock.monolithic.company.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotEmpty
        @Size(max = 50)
        String username,
        @NotEmpty
        String password) {
}
