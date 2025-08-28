package com.mock.monolithic.company.controller;

import com.mock.monolithic.company.dto.LoginRequest;
import com.mock.monolithic.company.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(service.authenticate(loginRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> test2() {
        log.info("ADMIN");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> test3() {
        log.info("USER");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}