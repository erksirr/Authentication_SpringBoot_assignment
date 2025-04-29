package com.example.auth_assignment.adapter.in.controller;

import com.example.auth_assignment.adapter.in.dto.LoginRequest;
import com.example.auth_assignment.adapter.in.dto.RegisterRequest;
import com.example.auth_assignment.domain.service.AuthServicePort;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServicePort authServicePort;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        System.out.println("path /auth/login ทำงานแล้ว username = " + request.getUsername() + ", password = " + request.getPassword());
        String token = authServicePort.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        System.out.println("path /auth/register ทำงาน แล้ว username = " + request.getUsername() + ", password = " + request.getPassword() + ", role = " + request.getRole());
        authServicePort.register(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
