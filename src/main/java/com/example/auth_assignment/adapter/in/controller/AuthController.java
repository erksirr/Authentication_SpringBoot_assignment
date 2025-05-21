package com.example.auth_assignment.adapter.in.controller;

import com.example.auth_assignment.adapter.in.dto.LoginRequest;
import com.example.auth_assignment.adapter.in.dto.RegisterRequest;
import com.example.auth_assignment.domain.service.AuthServicePort;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServicePort authServicePort;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        System.out.println("เข้า path /auth/login");
    
        String token = authServicePort.login(request.getUsername(), request.getPassword());
        Map<String, String> response = new HashMap<>();

        response.put("message", "User login successfully");
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterRequest request) {
        String token = authServicePort.register(request.getUsername(), request.getPassword(), request.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
