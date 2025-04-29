package com.example.auth_assignment.adapter.in.controller;

import com.example.auth_assignment.domain.model.User;
import com.example.auth_assignment.domain.service.AuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthServicePort authServicePort;

    @GetMapping("/user")
    public String userAccess() {
        User user = authServicePort.getCurrentUser();
        System.out.println("เข้า path /user");
        return "Welcome USER: " + user.getUsername();
    }

    @GetMapping("/admin")
    public String adminAccess() {
        User user = authServicePort.getCurrentUser();
        System.out.println("เข้า path /admin");
        return "Welcome ADMIN: " + user.getUsername();
    }
}